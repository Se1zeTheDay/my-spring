package com.njupt.springframework.context;

import com.njupt.springframework.beans.factory.base.ListableBeanFactory;
import com.njupt.springframework.context.event.base.ApplicationEventPublisher;

public interface ApplicationContext extends ListableBeanFactory, ApplicationEventPublisher {


}
