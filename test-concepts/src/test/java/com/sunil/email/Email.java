package com.sunil.email;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Email{
    private String sendTo;
    private String subject;
    private String content;
    private Format format;
}
