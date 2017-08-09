package br.com.dynara.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.dynara.agenda.dao.AlunoDAO;
import br.com.dynara.agenda.modelo.Aluno;

/**
 * Created by Dynara on 09/07/2017.
 */

public class EnviaDadosServidor extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog alertDialog;

    public EnviaDadosServidor( Context context){

        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        alertDialog = ProgressDialog.show(context,"Aguarde" , "Enviando para o servidor ...", true, true);
        alertDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        WebClient webClient = new WebClient();
        AlunoConverter converter = new AlunoConverter();
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();
        String json = converter.toJson(alunos);
        String resposta = webClient.post(json);

        return resposta ;
    }

    @Override
    protected void onPostExecute(String resposta) {
        alertDialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
