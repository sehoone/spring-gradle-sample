package com.sehoon.springgradlesample.common.mci.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

public class MciPropManager {

	private MciPropManager(){
		this.init();
	}
	
	private final Map<String, String> mciPropMap = new HashMap<>();
	
	private static class SingleTonHolder{
		private static final MciPropManager instance = new MciPropManager();
	}
	
	public static MciPropManager getInstance(){
		return SingleTonHolder.instance;
	}

	private void init() {
		Yaml y = new Yaml();
		InputStream resource = null;
		try {
			resource = new ClassPathResource("properties/mciprop.yml").getInputStream();
		} catch (IOException e) {
		}
		if(resource == null){
			return;
		}
		Properties props = new Properties();
        Map<String, Object> yamlMaps = y.load(resource);
		assignProperties(props, yamlMaps, null);
		propertiesToMap(props);
	}

	// yaml to properties format assign
	@SuppressWarnings("unchecked")
    private void assignProperties(Properties props, Map<String, Object> map, String path) {
        for (Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.isNotEmpty(path))
                key = path + "." + key;
            Object val = entry.getValue();
            if (val instanceof String) {
                props.put(key, val);
            } else if (val instanceof Map) {
                assignProperties(props, (Map<String, Object>) val, key);
            }
        }
    }

	// properties to hashmap and mciPropMap put
    private void propertiesToMap(Properties prop) {
        mciPropMap.putAll(prop.entrySet().stream().collect(
          Collectors.toMap(
            e -> String.valueOf(e.getKey()),
            e -> String.valueOf(e.getValue()),
            (prev, next) -> next, HashMap::new
        )));
    }

	public String getProp(String key){
		return mciPropMap.get(key);
	}
}