package br.com.dynara.agenda.dto;

import java.util.List;

import br.com.dynara.agenda.modelo.Aluno;

/**
 * Created by dynararicooliveira on 12/11/17.
 */

public class AlunoSync {
    private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }
}
