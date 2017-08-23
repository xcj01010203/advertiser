package com.xiaotu.advertiser.project.model.map;

import com.xiaotu.advertiser.dictionary.model.GoodsModel;
import com.xiaotu.advertiser.project.model.PlayMarkModel;

/**
 * 品类与剧本标记关联关系
 * @author xuchangjian 2017-6-19下午5:08:56
 */
public class PlayMarkGoodsMapModel {

	private String id;
	private GoodsModel goods;	//品类
	private PlayMarkModel playMark;	//剧本标记
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public GoodsModel getGoods() {
		return goods;
	}
	public void setGoods(GoodsModel goods) {
		this.goods = goods;
	}
	public PlayMarkModel getPlayMark() {
		return playMark;
	}
	public void setPlayMark(PlayMarkModel playMark) {
		this.playMark = playMark;
	}
}
