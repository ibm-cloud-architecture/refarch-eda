package application.demo;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import application.demo.RecordData;

public class RecordDataEncoder implements Encoder.Text<RecordData> {

	@Override
	public void init(EndpointConfig config) {}

	@Override
	public void destroy() {}

	@Override
	public String encode(RecordData recordData) throws EncodeException {
		return recordData.encode();
	}

}