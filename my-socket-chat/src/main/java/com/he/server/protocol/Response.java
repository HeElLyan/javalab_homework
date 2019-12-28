package com.he.server.protocol;

import com.he.server.dto.Dto;

public interface Response {
    <D extends Dto>void setData(D data);

}
