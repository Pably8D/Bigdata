package it.bigdata.ejb.service;

import it.bigdata.dto.Constants;
import it.bigdata.dto.OltpStatisticDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class OltpServiceImpl
 */
@Stateless
@Local(OltpService.class)
@LocalBean
public class OltpServiceImpl implements OltpService {
	/**
     * @see OltpService#getOltpStatistics()
     */
    public List<OltpStatisticDTO> getOltpStatistics() {
        
    	List<OltpStatisticDTO> result = new ArrayList<>();
    	
		result.addAll(createFakeData(Constants.DB_TYPE_SQL));
		result.addAll(createFakeData(Constants.DB_TYPE_NOSQL));
		result.addAll(createFakeData(Constants.DB_TYPE_NEWSQL));
    	
    	return result;
    }
    
    private List<OltpStatisticDTO> createFakeData(String dbType){
List<OltpStatisticDTO> result = new ArrayList<>();
    	
		//Insert SQL
    	OltpStatisticDTO insertDTO = new OltpStatisticDTO();
    	insertDTO.setDb(dbType);
    	insertDTO.setOperation(Constants.OPERATION_INSERT);
    	insertDTO.setSeconds(getRandomNumber());
    	insertDTO.setNumTransactions(Constants.CENTO);
    	result.add(insertDTO);
    	
    	insertDTO = new OltpStatisticDTO();
    	insertDTO.setDb(dbType);
    	insertDTO.setOperation(Constants.OPERATION_INSERT);
    	insertDTO.setSeconds(getRandomNumber());
    	insertDTO.setNumTransactions(Constants.MILLE);
    	result.add(insertDTO);
    	
    	insertDTO = new OltpStatisticDTO();
    	insertDTO.setDb(dbType);
    	insertDTO.setOperation(Constants.OPERATION_INSERT);
    	insertDTO.setSeconds(getRandomNumber());
    	insertDTO.setNumTransactions(Constants.DIECIMILA);
    	result.add(insertDTO);
    	
    	//Update SQL
    	OltpStatisticDTO updateDTO = new OltpStatisticDTO();
    	updateDTO.setDb(dbType);
    	updateDTO.setOperation(Constants.OPERATION_UPDATE);
    	updateDTO.setSeconds(getRandomNumber());
    	updateDTO.setNumTransactions(Constants.CENTO);
    	result.add(updateDTO);
    	
    	updateDTO = new OltpStatisticDTO();
    	updateDTO.setDb(dbType);
    	updateDTO.setOperation(Constants.OPERATION_UPDATE);
    	updateDTO.setSeconds(getRandomNumber());
    	updateDTO.setNumTransactions(Constants.MILLE);
    	result.add(updateDTO);
    	
    	updateDTO = new OltpStatisticDTO();
    	updateDTO.setDb(dbType);
    	updateDTO.setOperation(Constants.OPERATION_UPDATE);
    	updateDTO.setSeconds(getRandomNumber());
    	updateDTO.setNumTransactions(Constants.DIECIMILA);
    	result.add(updateDTO);
    	
    	//Delete SQL
    	OltpStatisticDTO deleteDTO = new OltpStatisticDTO();
    	deleteDTO.setDb(dbType);
    	deleteDTO.setOperation(Constants.OPERATION_DELETE);
    	deleteDTO.setSeconds(getRandomNumber());
    	deleteDTO.setNumTransactions(Constants.CENTO);
    	result.add(deleteDTO);
    	
    	deleteDTO = new OltpStatisticDTO();
    	deleteDTO.setDb(dbType);
    	deleteDTO.setOperation(Constants.OPERATION_DELETE);
    	deleteDTO.setSeconds(getRandomNumber());
    	deleteDTO.setNumTransactions(Constants.MILLE);
    	result.add(deleteDTO);
    	
    	deleteDTO = new OltpStatisticDTO();
    	deleteDTO.setDb(dbType);
    	deleteDTO.setOperation(Constants.OPERATION_DELETE);
    	deleteDTO.setSeconds(getRandomNumber());
    	deleteDTO.setNumTransactions(Constants.DIECIMILA);
    	result.add(deleteDTO);
    	
    	return result;
    }
    
    private Integer getRandomNumber(){
    	int min = 1;
    	int max = 300;
    	return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
