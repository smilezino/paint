package top.wangjun.service.impl;

import org.springframework.stereotype.Service;
import top.wangjun.service.IConfigService;

/**
 * @author zino
 * @date 2016-02-08 20:30
 */

@Service
public class ConfigService implements IConfigService {
	@Override
	public String getWatermarkText() {
		return "xxxx";
	}
}
