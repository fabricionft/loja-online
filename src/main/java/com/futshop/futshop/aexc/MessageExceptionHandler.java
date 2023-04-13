package com.futshop.futshop.aexc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageExceptionHandler {
    
    private Date timestamp;
    private Integer status;
    private String error;
    private String message;
}
