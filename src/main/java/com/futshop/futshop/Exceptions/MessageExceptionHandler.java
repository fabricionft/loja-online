package com.futshop.futshop.Exceptions;


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
    private Date data;
    private Integer status;
    private String erro;
    private String mensagem;
}
