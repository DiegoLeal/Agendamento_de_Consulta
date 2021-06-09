package com.agendamentodeconsulta.security.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailedListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

  @Override
  public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {

    System.out.println(event.toString());
  }
}
