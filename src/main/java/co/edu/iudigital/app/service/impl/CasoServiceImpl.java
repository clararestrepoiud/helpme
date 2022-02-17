package co.edu.iudigital.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.iudigital.app.dto.CasoDto;
import co.edu.iudigital.app.exception.BadRequestException;
import co.edu.iudigital.app.exception.ErrorDto;
import co.edu.iudigital.app.exception.RestException;
import co.edu.iudigital.app.model.Caso;
import co.edu.iudigital.app.repository.ICasoRepository;
import co.edu.iudigital.app.service.iface.ICasoService;

@Service
public class CasoServiceImpl implements ICasoService{

	@Autowired
	private ICasoRepository casoRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<CasoDto> findAll() throws RestException {
		List<Caso> casos = casoRepository.findAll();
		List<CasoDto> casosDto = new ArrayList<>();
		casos.stream().forEach(c->{
			CasoDto casoDto = new CasoDto();
			casoDto.setId(c.getId());
			casoDto.setFechaHora(c.getFechaHora());
			casoDto.setLongitud(c.getLongitud());
			casoDto.setLatitud(c.getLatitud());
			casoDto.setVisible(c.getVisible());
			casoDto.setDescripcion(c.getDescripcion());
			casoDto.setRmiUrl(c.getRmiUrl());
			casoDto.setUsuarioId(c.getUsuario().getId());
			casoDto.setImage(c.getUsuario().getImage());
			casoDto.setNombre(c.getUsuario().getNombre());
			casosDto.add(casoDto);
		});
		
		return casosDto;
		
	}  

	
	@Transactional
	@Override
	public Boolean visible(Boolean visible, Long Id) throws RestException {
		// TODO Auto-generated method stub
		
		return casoRepository.setVisible(visible, Id);
	}

	
	@Transactional
	@Override
	public Caso save(Caso caso) throws RestException {
		// TODO Auto-generated method stub
		if(Objects.isNull(caso))
		{
			throw new BadRequestException(ErrorDto.getErrorDto(
					HttpStatus.BAD_REQUEST.getReasonPhrase(), 
					"Mala petición", //TODO: CREAR CONSTANTE EN CONSUTIL
					HttpStatus.BAD_REQUEST.value())
				);
		}
		return casoRepository.save(caso);
	}


	@Transactional(readOnly = true)
	@Override
	public Caso findById(Long Id) throws RestException {
		// TODO Auto-generated method stub
		return casoRepository.findById(Id).get();
	}


}
