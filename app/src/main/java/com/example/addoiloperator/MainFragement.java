package com.example.addoiloperator;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.UnicodeSet;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.daimajia.slider.library.SliderLayout;
import com.example.addoiloperator.bean.CarCommon;
import com.example.addoiloperator.bean.Common;
import com.example.addoiloperator.utils.Utils;
import com.example.yunzhivoice.R;
import com.example.yunzhivoice.TTSUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainFragement extends Fragment {

    public View splashView = null;
    public View addoilpageView = null;
    public View addoilendpageView = null;
    public View addoilprocesspageView = null;
    public View operatorendpageView = null;
    public View line_carposition_left = null;
    public View line_carposition_right = null;
    public View line_carposition_top = null;
    public View line_carposition_bottom = null;
    public View line_carposition_true = null;
    public View line_invade = null;
    public TextView tv_addoilmount, tv_add_oil_gunno,tv_add_oil_type,tv_add_oil_end_amount,tv_add_oil_end_gunno,tv_add_oil_end_price,tv_add_oil_end_type,tv_add_oil_end_volume = null;
    public TextView tv_add_oil_process_amount,tv_add_oil_process_volume,tv_add_oil_process_type = null;
    public TextView tv_index_end ,tv_bottom_carNumber,tv_top_carNumber,tv_left_carNumber,tv_right_carNumber,tv_addoil_carnumber= null;
    public static final String AUTHORIZATION = "1";
    public static final String ADDOIL = "2";
    public static final String ADDOILEND = "3";
    public static final String OPERATOREND = "4";

    SliderLayout slider_layout = null;
    public String[]titles = new String[]{"活虾送到家","急速退款","新人福利"};
    public int[] images = new int[] {R.mipmap.guide_page_image_1,R.mipmap.guide_page_image_3,R.mipmap.guide_page_image_4};

    ArrayList<Integer> imgs = new ArrayList<Integer>();
    ArrayList<String> titles1 = new ArrayList<String>();
    Banner mBanner = null;

    public TextView tv_countdown = null;
    BackDownTimer backDownTimer = null;
    public Button btn_back_index = null;

    public static final int CAR_POSITION_TRUE = 0;
    public static final int CAR_POSITION_TOP = 1;
    public static final int CAR_POSITION_BOTTOM = 2;
    public static final int CAR_POSITION_LEFT = 3;
    public static final int CAR_POSITION_RIGHT = 4;

    public static final int CAR_NOT_INSTRUCTION = 0;
    public static final int CAR_INSTRUCTION = 1;


    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mainfragment, null);
        EventBus.getDefault().register(this);
        findView(v);
        return v;
    }

    public void findView(View v){
        splashView = v.findViewById(R.id.line_splashpage);
        addoilpageView = v.findViewById(R.id.line_addoilpage);
        addoilendpageView = v.findViewById(R.id.line_addoilendpage);
        addoilprocesspageView = v.findViewById(R.id.line_addoilprocesspage);
        operatorendpageView = v.findViewById(R.id.line_operator_end_page);

        line_carposition_left = v.findViewById(R.id.line_carposition_left);
        line_carposition_right = v.findViewById(R.id.line_carposition_right);
        line_carposition_top = v.findViewById(R.id.line_carposition_top);
        line_carposition_bottom = v.findViewById(R.id.line_carposition_bottom);
        line_carposition_true = v.findViewById(R.id.line_carposition_true);
        line_invade = v.findViewById(R.id.line_invade);

        tv_index_end = v.findViewById(R.id.tv_index_end);
        tv_index_end.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_index_end.getPaint().setColor(Color.RED);

        tv_bottom_carNumber = v.findViewById(R.id.tv_bottom_carNumber);
        tv_top_carNumber = v.findViewById(R.id.tv_top_carNumber);
        tv_left_carNumber = v.findViewById(R.id.tv_left_carNumber);
        tv_right_carNumber = v.findViewById(R.id.tv_right_carNumber);
        tv_addoil_carnumber = v.findViewById(R.id.tv_addoil_carnumber);

        tv_addoilmount = v.findViewById(R.id.tv_add_oil_amount);

        tv_add_oil_gunno = v.findViewById(R.id.tv_add_oil_gunno);
        tv_add_oil_type = v.findViewById(R.id.tv_add_oil_type);
        tv_add_oil_end_amount = v.findViewById(R.id.tv_add_oil_end_amount);
        tv_add_oil_end_gunno = v.findViewById(R.id.tv_add_oil_end_gunno);
        tv_add_oil_end_price = v.findViewById(R.id.tv_add_oil_end_price);
        tv_add_oil_end_type = v.findViewById(R.id.tv_add_oil_end_type);
        tv_add_oil_end_volume = v.findViewById(R.id.tv_add_oil_end_volume);
        tv_add_oil_process_amount = v.findViewById(R.id.tv_add_oil_process_amount);
        tv_add_oil_process_type = v.findViewById(R.id.tv_add_oil_process_type);
        tv_add_oil_process_volume = v.findViewById(R.id.tv_add_oil_process_volume);
        btn_back_index = v.findViewById(R.id.btn_back_index);
        tv_countdown = v.findViewById(R.id.tv_countdown);
        tv_countdown.setText("20");

        TextView tv_operator_1 = v.findViewById(R.id.tv_operator_1);
        TextView tv_operator_2 = v.findViewById(R.id.tv_operator_2);
        TextView tv_operator_3 = v.findViewById(R.id.tv_operator_3);
        tv_operator_1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_operator_2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_operator_3.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        imgs.add(R.mipmap.guide_page_image_1);
        imgs.add(R.mipmap.guide_page_image_3);
        imgs.add(R.mipmap.guide_page_image_4);

        titles1.add("活虾送到家");
        titles1.add("极速退款");
        titles1.add("新人福利");

        mBanner = v.findViewById(R.id.tv_activity_banner);
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        mBanner.setImageLoader(new MyImageLoader());
        //设置图片集合
        mBanner.setImages(imgs);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //mBanner.setBannerAnimation(Transformer.CubeIn);// Transformer.BackgroundToForeground
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(titles1);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);

        btn_back_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeOutToBack();
            }
        });
    }

    public void hideCarPosition(){
        line_carposition_left.setVisibility(View.GONE);
        line_carposition_right.setVisibility(View.GONE);
        line_carposition_top.setVisibility(View.GONE);
        line_carposition_bottom.setVisibility(View.GONE);
        line_carposition_true.setVisibility(View.GONE);
        line_invade.setVisibility(View.GONE);
    }

    public void showScreen(int leftVisibility,int rightVisibility,int topVisibility,int bottomVisibility,int trueVisibility,int invadeVisibility){
        splashView.setVisibility(View.GONE);
        addoilpageView.setVisibility(View.GONE);
        addoilprocesspageView.setVisibility(View.GONE);
        addoilendpageView.setVisibility(View.GONE);
        operatorendpageView.setVisibility(View.GONE);
        line_carposition_left.setVisibility(leftVisibility);
        line_carposition_right.setVisibility(rightVisibility);
        line_carposition_top.setVisibility(topVisibility);
        line_carposition_bottom.setVisibility(bottomVisibility);
        line_carposition_true.setVisibility(trueVisibility);
        line_invade.setVisibility(invadeVisibility);
    }

    public void speakContent(String content){
        TTSUtils.getInstance().speak(content);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getVideoRecoMessage(final CarCommon videoRecoMessage){
        System.out.println("yanyan videoRecoMessage="+videoRecoMessage.getMessage().getCameraID());
         String cameraID = videoRecoMessage.getMessage().getCameraID();
         String alarmType = videoRecoMessage.getMessage().getAlarmType();
         String alarmValue = videoRecoMessage.getMessage().getAlarmValue();
         String alarmLevel = videoRecoMessage.getMessage().getAlarmLevel();
         final String licensePlate = videoRecoMessage.getMessage().getLicensePlate();
         String pumpID = videoRecoMessage.getMessage().getPumpID();
        /**
         * 如果AlarmType=1 代表有入侵，如果AlarmType=2 代表车辆入侵
         */
        if(Integer.parseInt(alarmType)==1){
            switch (Integer.parseInt(alarmValue)){
                case CAR_NOT_INSTRUCTION:
                    speakContent("您已停好车辆，请使用昆仑好客e站APP下单，下单请选择机器人加油，请您熄火并解锁油箱盖");
                    showScreen(View.GONE,View.GONE,View.GONE, View.GONE,View.VISIBLE,View.GONE);
                    break;
                case CAR_INSTRUCTION:
                    speakContent("您已进入机器人工作范围,请离开工作区域！");
                    showScreen(View.GONE,View.GONE,View.GONE, View.GONE,View.GONE,View.VISIBLE);
                    break;
                default:break;
            }
         }
        /**
         * 如果AlarmType=2 代表车辆位置的变动
         */
        else{
            switch (Integer.parseInt(alarmValue)){
                case CAR_POSITION_TRUE:
                    speakContent("您已停好车辆，请使用昆仑好客e站APP下单，下单请选择机器人加油，请您熄火并解锁油箱盖");
                    showScreen(View.GONE,View.GONE,View.GONE, View.GONE,View.VISIBLE,View.GONE);
                    break;
                case CAR_POSITION_TOP:
                    speakContent("请您把车辆往前方挪动");
                    showScreen(View.GONE,View.GONE,View.VISIBLE, View.GONE,View.GONE,View.GONE);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_top_carNumber.setText(licensePlate);
                        }
                    });

                    break;
                case CAR_POSITION_BOTTOM:
                    speakContent("请您把车辆往后方挪动");
                    showScreen(View.GONE,View.GONE,View.GONE, View.VISIBLE,View.GONE,View.GONE);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_bottom_carNumber.setText(licensePlate);
                        }
                    });
                    break;
                case CAR_POSITION_LEFT:
                    speakContent("请您把车辆往左侧挪动");
                    showScreen(View.VISIBLE,View.GONE,View.GONE, View.GONE,View.GONE,View.GONE);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_left_carNumber.setText(licensePlate);
                        }
                    });
                    break;
                case CAR_POSITION_RIGHT:
                    speakContent("请您把车辆往右侧挪动");
                    showScreen(View.GONE,View.VISIBLE,View.GONE, View.GONE,View.GONE,View.GONE);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_right_carNumber.setText(licensePlate);
                        }
                    });
                    break;
                default:break;
            }
         }
         System.out.println("yanyan alarmType="+alarmType+",alarmValue="+alarmValue);
    }


    int enterPlay = 0;

    /**
     * 从Service中获得的数据
     * @param common
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDetail(final Common common){
            String optType = common.getMessage().getOptType();
            if (optType.equals(AUTHORIZATION)) {
                splashView.setVisibility(View.GONE);
                addoilpageView.setVisibility(View.VISIBLE);
                addoilprocesspageView.setVisibility(View.GONE);
                addoilendpageView.setVisibility(View.GONE);
                operatorendpageView.setVisibility(View.GONE);
                hideCarPosition();
                String amount = common.getMessage().getAmount();
                String pumpID = common.getMessage().getPumpID();
                String gradeID = common.getMessage().getGradeID();
                String licensePlate = common.getMessage().getLicensePlate();
                System.out.println("yanyan licensePlate="+licensePlate);
//                try {
//                    licensePlate = new String(licensePlate.getBytes("UTF-8"),"GB2312");
//
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                TTSUtils.getInstance().speak(licensePlate+"您已预授权成功，授权金额"+amount+"元，"+gradeID+"号油品,"+pumpID+"号枪，请您再次确认是否熄火并解锁油箱盖");

                tv_addoilmount.setText(amount);
                tv_add_oil_gunno.setText(pumpID);
                tv_add_oil_type.setText(gradeID);
                tv_addoil_carnumber.setText(licensePlate);
            } else if (optType.equals(ADDOIL)) {
                splashView.setVisibility(View.GONE);
                addoilpageView.setVisibility(View.GONE);
                addoilprocesspageView.setVisibility(View.VISIBLE);
                addoilendpageView.setVisibility(View.GONE);
                operatorendpageView.setVisibility(View.GONE);
                hideCarPosition();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(enterPlay%4==0){
                            TTSUtils.getInstance().speak("机器人加油中，请勿移动车辆，请勿进入其工作区域");
                        }
                        enterPlay++;
                        String amount = common.getMessage().getAmount();
                        String pumpID = common.getMessage().getPumpID();
                        String gradeID = common.getMessage().getGradeID();
                        String volume = common.getMessage().getVolume();
                        tv_add_oil_process_amount.setText(amount);
                        tv_add_oil_process_type.setText(gradeID);
                        tv_add_oil_process_volume.setText(volume);
                        if (mBanner != null) {
                            mBanner.start();
                        }
                    }
                });
            } else if (optType.equals(ADDOILEND)) {
                TTSUtils.getInstance().speak("机器人正在关闭油箱盖,请您稍候…");
                splashView.setVisibility(View.GONE);
                addoilpageView.setVisibility(View.GONE);
                addoilprocesspageView.setVisibility(View.GONE);
                addoilendpageView.setVisibility(View.VISIBLE);
                operatorendpageView.setVisibility(View.GONE);
                hideCarPosition();
                String amount = common.getMessage().getAmount();
                String pumpID = common.getMessage().getPumpID();
                String gradeID = common.getMessage().getGradeID();
                String price = common.getMessage().getPrice();
                String volume = common.getMessage().getVolume();
                tv_add_oil_end_amount.setText(amount);
                tv_add_oil_end_gunno.setText(pumpID);
                tv_add_oil_end_type.setText(gradeID);
                tv_add_oil_end_price.setText(price);
                tv_add_oil_end_volume.setText(volume);

            }else if(optType.equals(OPERATOREND)){
                TTSUtils.getInstance().speak("本次加油已完成，可以驶离加油位，欢迎您再次体验加油机器人");
                splashView.setVisibility(View.GONE);
                addoilpageView.setVisibility(View.GONE);
                addoilprocesspageView.setVisibility(View.GONE);
                addoilendpageView.setVisibility(View.GONE);
                operatorendpageView.setVisibility(View.VISIBLE);
                hideCarPosition();
                System.out.println("yanyan backDownTimer=" + backDownTimer);
                if (backDownTimer == null) {
                    backDownTimer = new BackDownTimer((long) (20 * 1000), 1000);
                    backDownTimer.start();
                }
            }
    }

    @Override
    public void onStart() {
        super.onStart();
        //slider_layout.startAutoCycle();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mBanner!=null){
            mBanner.stopAutoPlay();
        }
        TTSUtils.getInstance().stop();
        EventBus.getDefault().unregister(this);
    }

    class BackDownTimer extends CountDownTimer {
        public BackDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            final long time = millisUntilFinished / 1000L;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (time > 0) {
                        tv_countdown.setText("倒计时:"+time+"s");
                        if(time == 1) {
                          timeOutToBack();
                        }
                    } else {
                          timeOutToBack();
                    }
                }
            });
        }
        @Override
        public void onFinish() {
        }
    }

    public void timeOutToBack(){
        splashView.setVisibility(View.VISIBLE);
        addoilpageView.setVisibility(View.GONE);
        addoilprocesspageView.setVisibility(View.GONE);
        addoilendpageView.setVisibility(View.GONE);
        operatorendpageView.setVisibility(View.GONE);
        hideCarPosition();
        if (backDownTimer != null) {
            backDownTimer.cancel();
            backDownTimer = null;
        }
    }
}
