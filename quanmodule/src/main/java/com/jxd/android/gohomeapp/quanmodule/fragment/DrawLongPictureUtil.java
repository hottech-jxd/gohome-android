package com.jxd.android.gohomeapp.quanmodule.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.*;
import android.media.ExifInterface;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.*;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.*;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jxd.android.gohomeapp.quanmodule.R;
import com.jxd.android.gohomeapp.quanmodule.adapter.CenteredImageSpan;
import com.jxd.android.gohomeapp.quanmodule.bean.Constants;
import com.jxd.android.gohomeapp.quanmodule.bean.GoodsSourceEnum;
import com.jxd.android.gohomeapp.quanmodule.bean.SharePictureInfo;
import com.jxd.android.gohomeapp.quanmodule.util.AppUtil;
import com.jxd.android.gohomeapp.quanmodule.util.DensityUtils;
import com.jxd.android.gohomeapp.quanmodule.util.ImageUtils;
import com.jxd.android.gohomeapp.quanmodule.util.QrCodeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.drawlongpicturedemo.util
 * create at 2018/8/27  17:47
 * description:
 */
public class DrawLongPictureUtil extends LinearLayout {

	private final String TAG = "DrawLongPictureUtil";
	private Context context;
	//private SharedPreferences sp;
	private Listener listener;

	private SharePictureInfo shareInfo;
	// 保存下载后的图片url和路径键值对的链表
	private LinkedHashMap<String, String> localImagePathMap;

	private View rootView;
	private LinearLayout llTopView;
	private FrameLayout llContent;
	private LinearLayout llBottomView;

	private ImageView imgQrCode;

	// 长图的宽度，默认为屏幕宽度
	private int longPictureWidth;
	// 最终压缩后的长图宽度
	private int finalCompressLongPictureWidth;
	// 长图两边的间距
	private int picMargin;
	// 被认定为长图的长宽比
	private int maxSingleImageRatio = 3;
	private int widthTop = 0;
	private int heightTop = 0;

	private int widthContent = 0;
	private int heightContent = 0;

	private int widthBottom = 0;
	private int heightBottom = 0;

	private int widthPriceLabel=0;
	private int heightPriceLabel=0;
	private LinearLayout llPriceLabel;

	SimpleDraweeView ivPic;

	public void removeListener() {
		this.listener = null;
	}

	public interface Listener {

		/**
		 * 生成长图成功的回调
		 *
		 * @param path 长图路径
		 */
		void onSuccess(String path, String tag);

		/**
		 * 生成长图失败的回调
		 */
		void onFail();
	}

	public DrawLongPictureUtil(Context context) {
		super(context);

		init(context);
	}

	public DrawLongPictureUtil(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DrawLongPictureUtil(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	private void init(Context context) {
		this.context = context;
		longPictureWidth = DensityUtils.INSTANCE.getScreenWidth(context);
		picMargin = 40;
		rootView = LayoutInflater.from(context).inflate(R.layout.layout_share_goods_templete , this, false);
		//initView();
	}


	private void initView() {
		llTopView = rootView.findViewById(R.id.share_goods_templete_lay_top);
		//llContent = rootView.findViewById(R.id.share_goods_templete_lay_content);
		llBottomView = rootView.findViewById(R.id.share_goods_templete_lay_bottom);

		llPriceLabel = rootView.findViewById(R.id.share_goods_templete_lay_finalprice);

		TextView tvTitle=rootView.findViewById(R.id.share_goods_templete_title);
		TextView tvPrice = rootView.findViewById(R.id.share_goods_templete_price);
		TextView tvFinalPrice= rootView.findViewById(R.id.share_goods_templete_finalprice);
		TextView tvFinalPrice2= rootView.findViewById(R.id.share_goods_templete_finalprice2);
		TextView tvCouponPrice = rootView.findViewById(R.id.share_goods_templete_couponPrice);
		//TextView tvFinalPriceSym = rootView.findViewById(R.id.share_goods_templete_finalprice_symbol);
		imgQrCode = rootView.findViewById(R.id.share_goods_templete_qrcode);

		String title = shareInfo.getGoodsName();

		tvTitle.setText( shareInfo.getGoodsName() );
		tvPrice.setText("￥"+shareInfo.getPrice()+"元");
		tvFinalPrice.setText(shareInfo.getFinalPrice()+"元");
		tvFinalPrice2.setText(shareInfo.getFinalPrice());
		tvCouponPrice.setText(shareInfo.getCouponPrice());
		tvPrice.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);

		SpannableString spannableString = new SpannableString("^&& "+title);

		Integer logo = R.mipmap.pinduoduo;
		if( shareInfo.getGoodsSource() == GoodsSourceEnum.PingDuoDuo.getCode() ){
			logo = R.mipmap.pinduoduo;
		}else if(shareInfo.getGoodsSource() == GoodsSourceEnum.TaoBao.getCode() ){
			logo = R.mipmap.taobao;
		}

		CenteredImageSpan imageSpan = new CenteredImageSpan( context , logo);
		spannableString.setSpan(imageSpan, 0, 4, ImageSpan.ALIGN_BASELINE);
		tvTitle.setText( spannableString);

		Integer titleWidth = longPictureWidth - picMargin * 2;
		TextPaint contentPaint = tvTitle.getPaint();
		StaticLayout staticLayout = new StaticLayout( spannableString  , contentPaint, titleWidth , Layout.Alignment.ALIGN_NORMAL, 1.2F, 0, false);
		Integer  titleHeight = staticLayout.getHeight();

		tvTitle.setLayoutParams( new LinearLayout.LayoutParams( titleWidth, titleHeight ));

		layoutView(llTopView);
		layoutView(llBottomView);
		layoutView(llPriceLabel);

		widthTop = llTopView.getMeasuredWidth();
		heightTop = llTopView.getMeasuredHeight();

		widthBottom = llBottomView.getMeasuredWidth();
		heightBottom = llBottomView.getMeasuredHeight();

		widthPriceLabel = llPriceLabel.getMeasuredWidth();
		heightPriceLabel = llPriceLabel.getMeasuredHeight() + 40;

		//Log.d(TAG, "drawLongPicture layout top view = " + widthTop + " × " + heightTop);
		//Log.d(TAG, "drawLongPicture layout llContent view = " + widthContent + " × " + heightContent);
		//Log.d(TAG, "drawLongPicture layout bottom view = " + widthBottom + " × " + heightBottom);
	}

	/**
	 * 手动测量view宽高
	 */
	private void layoutView(View v) {
		int width = DensityUtils.INSTANCE.getScreenWidth(context) - picMargin*2;
		int height = DensityUtils.INSTANCE.getScreenHeight(context);

		v.layout(0, 0, width, height);
		int measuredWidth = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		int measuredHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		v.measure(measuredWidth, measuredHeight);
		v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
	}

	public void setData( SharePictureInfo info) {
		this.shareInfo = info;
		if (localImagePathMap != null) {
			localImagePathMap.clear();
		} else {
			localImagePathMap = new LinkedHashMap<>();
		}
		localImagePathMap.put("0" , info.getPicturePath());

		initView();
	}

	public void startDraw() {
		new Thread(new Runnable() {
			@Override public void run() {
				try {
					createQrCode();
					// 开始绘制view
					draw();
				}catch (Exception ex){
					if (listener != null) {
						listener.onFail();
					}
				}
			}
		}).start();
	}

	private void createQrCode(){
		String url = shareInfo.getLinkUrl();
		if(TextUtils.isEmpty(url)) return;
		Integer width = DensityUtils.INSTANCE.dip2px(context, 90);
		Bitmap bitmap = new QrCodeUtils().creatQRCode(url , width,width );
		imgQrCode.setImageBitmap(bitmap);
	}

	private Bitmap getLinearLayoutBitmap(LinearLayout relativeLayout, int w, int h) {
		Bitmap originBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(originBitmap);
		relativeLayout.draw(canvas );
		//return ImageUtil.resizeImage(originBitmap, longPictureWidth, h);
		return originBitmap;
	}

	private int getAllImageHeight() {
		int height = 0;
		for (int i = 0; i < localImagePathMap.size(); i++) {
			int[] wh = ImageUtil.getWidthHeight(localImagePathMap.get( String.valueOf( i) ));
			int w = wh[0];
			int h = wh[1];
			wh[0] = (longPictureWidth - (picMargin) * 2);
			wh[1] = (wh[0]) * h / w;
			float imgRatio = h / w;
			if (imgRatio > maxSingleImageRatio) {
				wh[1] = wh[0] * maxSingleImageRatio;
				Log.d(TAG, "getAllImageHeight w h > maxSingleImageRatio = " + Arrays.toString(wh));
			}
			height = height + wh[1];
		}
		height = height + DensityUtils.INSTANCE.dip2px(context, 6F) * localImagePathMap.size();
		Log.d(TAG, "---getAllImageHeight = " + height);
		return height;
	}

//	private int getImageHeight(String imagePath){
//		int[] wh = ImageUtil.getWidthHeight(imagePath);
//		int w = wh[0];
//		int h = wh[1];
//		wh[0] = (longPictureWidth - (picMargin) * 2);
//		wh[1] = (wh[0]) * h / w;
//		float imgRatio = h / w;
//		if (imgRatio > maxSingleImageRatio) {
//			wh[1] = wh[0] * maxSingleImageRatio;
//			Log.d(TAG, "getAllImageHeight w h > maxSingleImageRatio = " + Arrays.toString(wh));
//		}
//		return wh[1];
//	}

//	private Bitmap getSingleBitmap(String path) {
//		int[] wh = ImageUtil.getWidthHeight(path);
//		final int w = wh[0];
//		final int h = wh[1];
//		wh[0] = (longPictureWidth - (picMargin) * 2);
//		wh[1] = (wh[0]) * h / w;
//		Bitmap bitmap = null;
//		try {
//			// 长图，只截取中间一部分
//			float imgRatio = h / w;
//			if (imgRatio > maxSingleImageRatio) {
//				wh[1] = wh[0] * maxSingleImageRatio;
//				Log.d(TAG, "getSingleBitmap w h > maxSingleImageRatio = " + Arrays.toString(wh));
//			}
//			bitmap = Glide.with(context).load(path).asBitmap().centerCrop().into(wh[0], wh[1]).get();
//			Log.d(TAG, "getSingleBitmap glide bitmap w h = " + bitmap.getWidth() + " , " + bitmap.getHeight());
//			return bitmap;
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		} catch (OutOfMemoryError e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return bitmap;
//	}

	private Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		if (bitmap == null) {
			return null;
		}
		try {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
			final Canvas canvas = new Canvas(output);
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
			paint.setAntiAlias(true);
			paint.setDither(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.BLACK);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			canvas.drawBitmap(bitmap, src, rect, paint);
			Log.d(TAG, "getRoundedCornerBitmap w h = " + output.getWidth() + " × " + output.getHeight());
			return output;
		} catch (Exception e) {
			e.printStackTrace();
			return bitmap;
		}
	}


	private void draw() {
		// 计算出最终生成的长图的高度 = 上、中、图片总高度、下等个个部分加起来
		int allBitmapHeight = heightTop + heightBottom;

		int imageHeight = getAllImageHeight();
		int imageWidth = (longPictureWidth - picMargin* 2);
		// 计算图片的总高度
		allBitmapHeight = allBitmapHeight + imageHeight + DensityUtils.INSTANCE.dip2px(context, 40);
		//当总长度小于 屏幕的长度是，设置为屏幕的高度，解决背景黑色问题
		int longScreenHeight= DensityUtils.INSTANCE.getScreenHeight(context);
		if(allBitmapHeight < longScreenHeight){
			allBitmapHeight = longScreenHeight;
		}

		//创建空白画布
		Bitmap.Config config = Bitmap.Config.ARGB_4444;
		Bitmap bitmapAll;
		try {
			bitmapAll = Bitmap.createBitmap(longPictureWidth, allBitmapHeight, config);
		} catch (Exception e) {
			e.printStackTrace();
			config = Bitmap.Config.RGB_565;
			bitmapAll = Bitmap.createBitmap(longPictureWidth, allBitmapHeight, config);
		}
		Canvas canvas = new Canvas(bitmapAll);
		canvas.drawColor(Color.WHITE);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setFilterBitmap(true);

		// 绘制top view
		canvas.drawBitmap(getLinearLayoutBitmap(llTopView, widthTop, heightTop), picMargin, 0, paint);
		//canvas.save();

		// 绘制图片view
		//canvas.restore();

		Bitmap bitmapTemp;
		int imageRadius = DensityUtils.INSTANCE.dip2px(context, 5F);

		String picPath = shareInfo.getPicturePath();
		bitmapTemp = ImageUtil.getImageBitmap(picPath, longPictureWidth , longScreenHeight );
		bitmapTemp = ImageUtils.scale(bitmapTemp , imageWidth , imageHeight );

		Bitmap roundBitmap = getRoundedCornerBitmap(bitmapTemp, imageRadius);
		int top = heightTop +  DensityUtils.INSTANCE.dip2px(context, 13);

		if (roundBitmap != null) {
			canvas.drawBitmap(roundBitmap, picMargin, top, paint);
		}

		//绘制价格标签
		Integer labelX = longPictureWidth - picMargin - widthPriceLabel;
		Integer labelY = top + imageHeight +  - heightPriceLabel - DensityUtils.INSTANCE.dip2px(context, 40);
		canvas.drawBitmap( getLinearLayoutBitmap( llPriceLabel , widthPriceLabel , heightPriceLabel ) ,  labelX , labelY , paint );

		// 绘制bottom view
		canvas.drawBitmap(getLinearLayoutBitmap(llBottomView, widthBottom, heightBottom), picMargin,
				(top + imageHeight + DensityUtils.INSTANCE.dip2px(context, 16)), paint);

		// 生成最终的文件，并压缩大小，这里使用的是：implementation 'com.github.nanchen2251:CompressHelper:1.0.5'
		try {

		    bitmapAll = ImageUtils.compressByQuality(bitmapAll , 50 , true);

			String resultPath = Constants.INSTANCE.getImageDirPath()+"share/"+ String.valueOf( System.currentTimeMillis())+".jpg";
			AppUtil.INSTANCE.save( bitmapAll , new File( resultPath ) , Bitmap.CompressFormat.JPEG,true);
//			float imageRatio = ImageUtil.getImageRatio(path);
//			if (imageRatio >= 10) {
//				finalCompressLongPictureWidth = 750;
//			} else if (imageRatio >= 5 && imageRatio < 10) {
//				finalCompressLongPictureWidth = 900;
//			} else {
//				finalCompressLongPictureWidth = longPictureWidth;
//			}
//			String result;
			// 由于长图一般比较大，所以压缩时应注意OOM的问题，这里并不处理OOM问题，请自行解决。
//			try {
//				result = new CompressHelper.Builder(context).setMaxWidth(finalCompressLongPictureWidth)
//					.setMaxHeight(Integer.MAX_VALUE) // 默认最大高度为960
//					.setQuality(80)    // 默认压缩质量为80
//					.setFileName("长图_" + System.currentTimeMillis()) // 设置你需要修改的文件名
//					.setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
//					.setDestinationDirectoryPath(
//						Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
//							+ "/长图分享/")
//					.build()
//					.compressToFile(new File(path))
//					.getAbsolutePath();
//			} catch (OutOfMemoryError e) {
//				e.printStackTrace();

//				finalCompressLongPictureWidth = finalCompressLongPictureWidth / 2;
//				result = new CompressHelper.Builder(context).setMaxWidth(finalCompressLongPictureWidth)
//					.setMaxHeight(Integer.MAX_VALUE) // 默认最大高度为960
//					.setQuality(50)    // 默认压缩质量为80
//					.setFileName("长图_" + System.currentTimeMillis()) // 设置你需要修改的文件名
//					.setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
//					.setDestinationDirectoryPath(
//						Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
//							+ "/长图分享/")
//					.build()
//					.compressToFile(new File(path))
//					.getAbsolutePath();
//			}

			insertImageToSystem(resultPath);

			Log.d(TAG, "最终生成的长图路径为：" + resultPath);
			if (listener != null) {
				listener.onSuccess(resultPath , shareInfo.getTag());
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (listener != null) {
				listener.onFail();
			}
		}
	}

	/**
	 * 生成图片以后，告诉系统我生成了一张图片
	 * @param imagePath
	 * @return
	 */
	private String insertImageToSystem(  String imagePath) {
		String url = "";
		try {
			String picName = AppUtil.INSTANCE.getFileName(imagePath);
			url = MediaStore.Images.Media.insertImage(context.getContentResolver(), imagePath, picName , picName );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return url;
	}

}


class ImageUtil{

	public static Bitmap resizeImage(Bitmap origin, int newWidth, int newHeight) {
		if (origin == null) {
			return null;
		}
		int height = origin.getHeight();
		int width = origin.getWidth();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
		if (!origin.isRecycled()) {
			origin.recycle();
		}
		return newBM;
	}

	public static int[] getWidthHeight(String imagePath) {
		if (TextUtils.isEmpty(imagePath)) {
			return new int[] { 0, 0 };
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		try {
			Bitmap originBitmap = BitmapFactory.decodeFile(imagePath, options);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 使用第一种方式获取原始图片的宽高
		int srcWidth = options.outWidth;
		int srcHeight = options.outHeight;

		// 使用第二种方式获取原始图片的宽高
		if (srcHeight <= 0 || srcWidth <= 0) {
			try {
				ExifInterface exifInterface = new ExifInterface(imagePath);
				srcHeight =
						exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);
				srcWidth =
						exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 使用第三种方式获取原始图片的宽高
		if (srcWidth <= 0 || srcHeight <= 0) {
			Bitmap bitmap2 = BitmapFactory.decodeFile(imagePath);
			if (bitmap2 != null) {
				srcWidth = bitmap2.getWidth();
				srcHeight = bitmap2.getHeight();
				try {
					if (!bitmap2.isRecycled()) {
						bitmap2.recycle();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return new int[] { srcWidth, srcHeight };
	}


	public static Bitmap getImageBitmap(String srcPath, float maxWidth, float maxHeight) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int originalWidth = newOpts.outWidth;
		int originalHeight = newOpts.outHeight;

		float be = 1;
		if (originalWidth > originalHeight && originalWidth > maxWidth) {
			be = originalWidth / maxWidth;
		} else if (originalWidth < originalHeight && originalHeight > maxHeight) {
			be = newOpts.outHeight / maxHeight;
		}
		if (be <= 0) {
			be = 1;
		}

		newOpts.inSampleSize = (int) be;
		newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		newOpts.inDither = false;
		newOpts.inPurgeable = true;
		newOpts.inInputShareable = true;

		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}

		try {
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		} catch (OutOfMemoryError e) {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
			Runtime.getRuntime().gc();
		} catch (Exception e) {
			Runtime.getRuntime().gc();
		}

//		if (bitmap != null) {
//			bitmap = rotateBitmapByDegree(bitmap, getBitmapDegree(srcPath));
//		}
		return bitmap;
	}

}