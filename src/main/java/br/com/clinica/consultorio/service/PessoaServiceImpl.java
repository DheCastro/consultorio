package br.com.clinica.consultorio.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.clinica.consultorio.model.Pessoa;
import br.com.clinica.consultorio.repository.PessoaRepository;

@Service
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	
	@Override
	public Pessoa salvar(Pessoa pessoa) throws Exception {
		if(verificarSeRegistroJaExiste(pessoa)){
			throw new Exception("Erro");
		};		
		return pessoaRepository.save(pessoa);
	}
	
	@Override
	public Pessoa buscarPorId(Long id) throws Exception {
		Optional<Pessoa> optionalEntity = pessoaRepository.findById(id);
		
		if(!optionalEntity.isPresent()) {
			throw new Exception("Erro");
		}
		return optionalEntity.get();
	}
	
	@Override
	public Map<String, Object> execute(HttpServletRequest request) {
		
		int start = Integer.parseInt(request.getParameter("start"));
		int lenght = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));
		
		int current = currentPage(start, lenght);
		
		String column = columnName(request);
		Sort.Direction direction = orderBy(request);
		String search = searchBy(request);
		
		Pageable pageable = PageRequest.of(current, lenght, direction, column);
		
		Page<Pessoa> page = queryBy(search, pessoaRepository, pageable);
		
		Map<String, Object> json = new LinkedHashMap<>();
		json.put("draw", draw);
		json.put("recordsTotal", page.getTotalElements());
		json.put("recordsFiltered", page.getTotalElements());
		json.put("data", page.getContent());
		
		return json;
	}
	
	private Page<Pessoa> queryBy(String search, PessoaRepository repository, Pageable pageable) {		
		
		if (search.isEmpty()) {
			return repository.findAll(pageable);
		}

		/*if (search.matches("^[0-9]+([.,][0-9]{2})?$")) {
			search = search.replace(",", ".");
			return repository.findByPreco(new BigDecimal(search), pageable);
		}
		
		return repository.findByTituloOrSiteOrCategoria(search, pageable);*/
		return repository.findAll(pageable);
	}
	
	private String searchBy(HttpServletRequest request) {
		
		return request.getParameter("search[value]").isEmpty()
				? ""
				: request.getParameter("search[value]");
	}	

	private Direction orderBy(HttpServletRequest request) {
		String order = request.getParameter("order[0][dir]");
		Sort.Direction sort = Sort.Direction.ASC;
		if (order.equalsIgnoreCase("desc")) {
			sort = Sort.Direction.DESC;
		}
		return sort;
	}

	private String columnName(HttpServletRequest request) {
		int iCol = Integer.parseInt(request.getParameter("order[0][column]"));
		return Pessoa.cols[iCol];
	}

	private int currentPage(int start, int lenght) {
		//0			1			2
		//0-9 |	10-19 	| 20-29
		return start / lenght;
	}
	
	
	@Override
	public boolean verificarSeRegistroJaExiste(Pessoa pessoa) throws Exception {

		if(Objects.isNull(pessoa)) {
			throw new Exception("Erro");
		}
		
		Long quantidade = null;
		
		if(Objects.isNull(pessoa.getId())) {
			quantidade = pessoaRepository.countByNome(pessoa.getNome());
		} else {
			quantidade = pessoaRepository.countByNomeAndIdNot(pessoa.getNome(), pessoa.getId());
		}		
		return quantidade > 0;
	}
}
