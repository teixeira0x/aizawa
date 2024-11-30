package com.teixeira0x.aizawa.core.action;

public interface Action<T> {
  ActionResult execute(T event);
}
