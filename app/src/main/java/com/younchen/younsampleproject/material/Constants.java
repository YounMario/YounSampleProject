package com.younchen.younsampleproject.material;

import com.younchen.younsampleproject.material.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public class Constants {

    public static final String[] HEAD_IMG =  {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492969703963&di=99bc83e5726598b331b0cad3fe6b8e11&imgtype=0&src=http%3A%2F%2Fimg.dongman.fm%2Fpublic%2Fa983685029e9ff06ba781768668f3d79.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492969765741&di=012bf5f1dd4e9c6fc176adc8c71e99f6&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fvideo%2F43%2F4334a0b86c5f4717c02c40435424b9b2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492969824907&di=22edb4eff39599e79e525fa77a80a42a&imgtype=0&src=http%3A%2F%2Fimg.szhk.com%2FImage%2F2015%2F11%2F12%2F1447297124469.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492969852317&di=ed6b4bbcdc2b0e448d1b41005d872bb8&imgtype=0&src=http%3A%2F%2Fi3.hoopchina.com.cn%2Fblogfile%2F201602%2F28%2FBbsImg145663906833985_512x512.png",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492969929362&di=88ce895af1f244b1f6750fb112c77f91&imgtype=0&src=http%3A%2F%2Facg.ad2iction.com%2Fwp-content%2Fuploads%2F2016%2F06%2F1010-550x309.jpg"
    };

    public static final String[] NAME = {
            "秃头披风侠",
            "一拳",
            "夏萝莉",
            "音浪",
            "合伙"
    };

    public static final String[] MESSAGE = {
            "奥巴梅扬又他妈就糗了， 他太牛逼了得， 大舅啊 ，不吃冰的",
            "一拳， 刀怒斩学一调， 哈哈哈， 删号买冲云霄，长坡版咯咯哒",
            "夏萝莉，之城景岳国，知识就是力量， 吕轻侯和 呵呵大一块了在",
            "音浪，人狼金城郡， 狗证吧， 暗恋成的山哈哈哈， 金城武扮演jin kazama",
            "合伙,你妹呀 你是你大爷的儿子的表弟还是堂弟我不知道！！！"
    };

    public static List<Contact> DEFULT_CONTACT_DATA_LIST = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            contact.headImageUrl = HEAD_IMG[i % 5];
            contact.message = MESSAGE[i % 5];
            contact.name = NAME[i % 5];
            DEFULT_CONTACT_DATA_LIST.add(contact);
        }
    }


}
