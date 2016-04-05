package com.silent.framwork.model;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.silent.framwork.entity.BaseEntity;
import com.silent.framwork.exception.FailureException;

public abstract class Model {
	private String TAG = this.getClass().getSimpleName();

	public Object analysisJSON(String json, Type listType)
			throws FailureException {
		if (json.contains("\\")) {
			json = json.replace("\\", "");
		}
		Gson g = new Gson();
		BaseEntity mBaseEntity = g.fromJson(json, listType);
		if (null != mBaseEntity) {
			if (mBaseEntity.getStatus() == 200)
				return mBaseEntity.getData();
			else
				throw new FailureException(mBaseEntity.getStatus(),
						mBaseEntity.getMsg());
		} else {
			return null;
		}
	}

}
