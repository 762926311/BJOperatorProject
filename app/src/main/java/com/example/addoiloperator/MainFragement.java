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
    public String[]titles = new String[]{"???????????????","????????????","????????????"};
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

        titles1.add("???????????????");
        titles1.add("????????????");
        titles1.add("????????????");

        mBanner = v.findViewById(R.id.tv_activity_banner);
        //??????banner??????
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //?????????????????????
        mBanner.setImageLoader(new MyImageLoader());
        //??????????????????
        mBanner.setImages(imgs);
        //??????banner????????????
        mBanner.setBannerAnimation(Transformer.Default);
        //mBanner.setBannerAnimation(Transformer.CubeIn);// Transformer.BackgroundToForeground
        //????????????????????????banner???????????????title??????
        mBanner.setBannerTitles(titles1);
        //??????????????????????????????true
        mBanner.isAutoPlay(true);
        //??????????????????
        mBanner.setDelayTime(3000);
        //???????????????????????????banner???????????????????????????
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
         * ??????AlarmType=1 ????????????????????????AlarmType=2 ??????????????????
         */
        if(Integer.parseInt(alarmType)==1){
            switch (Integer.parseInt(alarmValue)){
                case CAR_NOT_INSTRUCTION:
                    speakContent("??????????????????????????????????????????e???APP????????????????????????????????????????????????????????????????????????");
                    showScreen(View.GONE,View.GONE,View.GONE, View.GONE,View.VISIBLE,View.GONE);
                    break;
                case CAR_INSTRUCTION:
                    speakContent("?????????????????????????????????,????????????????????????");
                    showScreen(View.GONE,View.GONE,View.GONE, View.GONE,View.GONE,View.VISIBLE);
                    break;
                default:break;
            }
         }
        /**
         * ??????AlarmType=2 ???????????????????????????
         */
        else{
            switch (Integer.parseInt(alarmValue)){
                case CAR_POSITION_TRUE:
                    speakContent("??????????????????????????????????????????e???APP????????????????????????????????????????????????????????????????????????");
                    showScreen(View.GONE,View.GONE,View.GONE, View.GONE,View.VISIBLE,View.GONE);
                    break;
                case CAR_POSITION_TOP:
                    speakContent("??????????????????????????????");
                    showScreen(View.GONE,View.GONE,View.VISIBLE, View.GONE,View.GONE,View.GONE);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_top_carNumber.setText(licensePlate);
                        }
                    });

                    break;
                case CAR_POSITION_BOTTOM:
                    speakContent("??????????????????????????????");
                    showScreen(View.GONE,View.GONE,View.GONE, View.VISIBLE,View.GONE,View.GONE);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_bottom_carNumber.setText(licensePlate);
                        }
                    });
                    break;
                case CAR_POSITION_LEFT:
                    speakContent("??????????????????????????????");
                    showScreen(View.VISIBLE,View.GONE,View.GONE, View.GONE,View.GONE,View.GONE);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_left_carNumber.setText(licensePlate);
                        }
                    });
                    break;
                case CAR_POSITION_RIGHT:
                    speakContent("??????????????????????????????");
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
     * ???Service??????????????????
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
                TTSUtils.getInstance().speak(licensePlate+"????????????????????????????????????"+amount+"??????"+gradeID+"?????????,"+pumpID+"?????????????????????????????????????????????????????????");

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
                            TTSUtils.getInstance().speak("?????????????????????????????????????????????????????????????????????");
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
                TTSUtils.getInstance().speak("??????????????????????????????,???????????????");
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
                TTSUtils.getInstance().speak("????????????????????????????????????????????????????????????????????????????????????");
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
                        tv_countdown.setText("?????????:"+time+"s");
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
