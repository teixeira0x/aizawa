package com.teixeira.aizawa.core.action;

public interface Action<T> {
  ActionResult execute(T event);
}
