package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.Request;
import com.piedpiper.gib.protocol.Response;

public interface Handler<T extends Request, R extends Response> {
        R handle(T request);
}
