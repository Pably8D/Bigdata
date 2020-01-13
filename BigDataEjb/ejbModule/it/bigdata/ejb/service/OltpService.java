package it.bigdata.ejb.service;

import java.util.List;

import it.bigdata.dto.OltpStatisticDTO;

public interface OltpService {

	public List<OltpStatisticDTO> getOltpStatistics();
	
}
