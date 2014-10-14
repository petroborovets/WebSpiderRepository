package com.softserveinc.Component.Google;

import com.softserveinc.DTO.GoogleUrlDTO;
import com.softserveinc.DTO.SpiderResultDTO;
import com.softserveinc.Entity.GoogleEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by petroborovets on 10/14/14.
 */
@Component
public class GoogleUtils {

    public static ArrayList<GoogleUrlDTO> getDTOsFromEntity(ArrayList<GoogleEntity> googleEntities) {
        ArrayList<GoogleUrlDTO> googleUrlDTOs = new ArrayList<GoogleUrlDTO>();
        for (GoogleEntity googleEntity : googleEntities) {
            googleUrlDTOs.add(getDTOFromEntity(googleEntity));
        }

        return googleUrlDTOs;
    }

    public static GoogleUrlDTO getDTOFromEntity(GoogleEntity googleEntity) {
        GoogleUrlDTO googleUrlDTO = new GoogleUrlDTO();

        googleUrlDTO.setId(googleEntity.getId());
        googleUrlDTO.setUrl(googleEntity.getUrl());
        googleUrlDTO.setUrlInfo(googleEntity.getUrlInfo());

        return googleUrlDTO;
    }

    public static ArrayList<SpiderResultDTO> getLogDTOs(ArrayList<GoogleEntity> googleEntities) {
        ArrayList<SpiderResultDTO> spiderResultDTOs = new ArrayList<SpiderResultDTO>();

        for (GoogleEntity googleEntity : googleEntities) {
            SpiderResultDTO spiderResultDTO = new SpiderResultDTO();
            spiderResultDTO.setId(googleEntity.getId());
            spiderResultDTO.setVacancyURL(googleEntity.getUrl());
            spiderResultDTOs.add(spiderResultDTO);

            if (spiderResultDTO.getId() == 0) {
                spiderResultDTO.setError(true);
                spiderResultDTO.setErrorDescription("URL already exists in DB");
            }

        }
        return spiderResultDTOs;
    }
}
