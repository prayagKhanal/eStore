package com.jeenya.jeenyastore.slider;

import android.content.Context;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;

/**
 * Created by hp on 6/8/2015.
 */
public class SliderImage {

    public static String[] getSliderImage() {
        String[] url_maps;
        url_maps = new String[]{
                "http://www.isenseven.tv/sites/default/files/styles/large/public/field/image/00_clothing_12-13_iTV_banner.jpg",
                "http://www.fcthanglong.com.vn/Uploads/Album/clothing-banner.jpg",
                "https://emilyssanctuary.files.wordpress.com/2013/05/lipsy-clothing-facebook-banner.png",
                "http://140953510.r.cdn77.net/Nookie/Customers_File/website/bgimages/924c5643-1297-467e-be55-2c11ca246e93/slide_slide_NT14web_banner_FLORAL1.jpg"
        };
        return url_maps;
    }

    public static void showSlider(String[] imageURL, Context home, SliderLayout sliderShow) {

        for (int i = 0; i < imageURL.length; i++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(home);
            // initialize a SliderLayout
            defaultSliderView
                    //.description(name)
                    .image(imageURL[i])
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //.setOnSliderClickListener(this);

            sliderShow.addSlider(defaultSliderView);
            sliderShow.setPresetTransformer(SliderLayout.Transformer.Default);
            sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderShow.setCustomAnimation(new DescriptionAnimation());
            sliderShow.setDuration(4000);

        }
    }


    //For Test

    public static void showSlider(ArrayList<String> imageURL, Context home, SliderLayout sliderShow) {

        for (int i = 0; i < imageURL.size(); i++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(home);
            // initialize a SliderLayout
            defaultSliderView
                    //.description(name)
                    .image(imageURL.get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //.setOnSliderClickListener(this);
            sliderShow.addSlider(defaultSliderView);
            sliderShow.setPresetTransformer(SliderLayout.Transformer.Default);
            sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderShow.setCustomAnimation(new DescriptionAnimation());
            sliderShow.setDuration(4000);
        }


    }
}
