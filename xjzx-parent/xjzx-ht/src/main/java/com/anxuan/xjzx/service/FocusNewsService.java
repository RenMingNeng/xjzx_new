package com.anxuan.xjzx.service;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.FocusNews;

public interface FocusNewsService extends BaseService<FocusNews>{

	FocusNews getFocusNewsById(int id);

}
