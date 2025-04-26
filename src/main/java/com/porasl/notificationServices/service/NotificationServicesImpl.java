package com.porasl.notificationServices.service;

package com.inrik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inrik.dao.ConfigDao;
import com.inrik.domain.ConfigInfo;

@Service
@Transactional
public class ConfigServiceImpl implements ConfigService {
    
    private ConfigDao configDao;

    @Override
    public void save(ConfigInfo configInfo) {
       configDao.save(configInfo);
    }

    @Override
    public List<ConfigInfo> findAll() {
        return configDao.findAll();
    }

    public void setConfigDao(ConfigDao configDao){
        this.configDao = configDao;
    }

    public ConfigDao getConfigDao(){
        return configDao;
    }

	@Override
	public void update(ConfigInfo config) {
		 configDao.updateConfig(config.getId(),config.getConfigName(),config.getConfigValue());
		
	}

	@Override
	public void delete(ConfigInfo config) {
		 configDao.delete(config);
		
	}
    
}
