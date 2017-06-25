package com.dalingge.awesome.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dalingge.awesome.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dingboyang on 2017/6/25.
 */

public class ScreenshotUtil {

    private final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() ;
    private static String pathfile = FILE_SAVEPATH + "/ScreenshotUtil.png";

    /**
     * 因为课表是可以滑动 的所以截取
     * 截取scrollview的屏幕
     **/
    public static void getBitmapByView(Context mContext, final ViewGroup viewGroup) {
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(viewGroup.getWidth(), viewGroup.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        viewGroup.draw(canvas);

        Bitmap head = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.share_term_table_header);
        Bitmap foot = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.share_term_table_footer);
        Bitmap v = toConformBitmap(head, bitmap, foot);

        File savedir = new File(FILE_SAVEPATH);
        if (!savedir.exists()) {
            savedir.mkdirs();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(pathfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(mContext,"保存失败",Toast.LENGTH_SHORT).show();
        }
        try {
            if (null != out) {
                v.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
            Toast.makeText(mContext,"保存成功",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(mContext,"保存失败",Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 合并图片
     *
     * @param head
     * @param body
     * @param foot
     * @return
     */
    public static Bitmap toConformBitmap(Bitmap head, Bitmap body, Bitmap foot) {
        if (head == null) {
            return null;
        }
        int headWidth = head.getWidth();
        int bodyWidth = body.getWidth();
        int footWidth = foot.getWidth();

        int headHeight = head.getHeight();
        int bodyHeight = body.getHeight();
        int footHeight = foot.getHeight();
        //生成三个图片合并大小的Bitmap
        Bitmap newbmp = Bitmap.createBitmap(bodyWidth, headHeight + bodyHeight + footHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        cv.drawBitmap(head, 0, 0, null);// 在 0，0坐标开始画入headBitmap

        //因为手机不同图片的大小的可能小了 就绘制白色的界面填充剩下的界面
        if (headWidth < bodyWidth) {
            System.out.println("绘制头");
            Bitmap ne = Bitmap.createBitmap(bodyWidth - headWidth, headHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ne);
            canvas.drawColor(Color.WHITE);
            cv.drawBitmap(ne, headWidth, 0, null);
        }
        cv.drawBitmap(body, 0, headHeight, null);// 在 0，headHeight坐标开始填充课表的Bitmap
        cv.drawBitmap(foot, 0, headHeight + bodyHeight, null);// 在 0，headHeight + kebiaoheight坐标开始填充课表的Bitmap
        //因为手机不同图片的大小的可能小了 就绘制白色的界面填充剩下的界面
        if (footWidth < bodyWidth) {
            System.out.println("绘制");
            Bitmap ne = Bitmap.createBitmap(bodyWidth - footWidth, footHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ne);
            canvas.drawColor(Color.WHITE);
            cv.drawBitmap(ne, footWidth, headHeight + bodyHeight, null);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        //回收
        head.recycle();
        body.recycle();
        foot.recycle();
        return newbmp;
    }

}
