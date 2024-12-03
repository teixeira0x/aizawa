package com.teixeira0x.aizawa.core.service.exception;

public class ServiceException extends Throwable {
  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
