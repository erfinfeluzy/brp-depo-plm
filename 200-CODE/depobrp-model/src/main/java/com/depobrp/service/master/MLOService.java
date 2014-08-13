package com.depobrp.service.master;

import java.util.List;

import com.depobrp.model.master.MLO;
import com.depobrp.service.common.ObjectService;


public interface MLOService extends ObjectService {

	List<MLO> getAllByFilter(MLO mlo);
}
