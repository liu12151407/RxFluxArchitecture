package com.huyingbao.module.gan.ui.random.action;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public interface RandomAction {
    String TO_SHOW_DATA = "to_show_data";

    String GET_PRODUCT_LIST = "get_product_list";

    void getProductList(String category, int count, int page);
}
