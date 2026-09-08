package br.com.contacerta.servicos;
import android.content.*;import android.database.*;import android.database.sqlite.*;import java.io.*;import java.util.*;

public class DB extends SQLiteOpenHelper{
 static final String NAME="conta_certa.db"; Context ctx;
 DB(Context c){super(c,NAME,null,45);ctx=c;}
 public void onCreate(SQLiteDatabase d){
  String[] sql={
   "CREATE TABLE clientes(id INTEGER PRIMARY KEY AUTOINCREMENT,nome TEXT,documento TEXT,telefone TEXT,email TEXT,limite_fiado REAL DEFAULT 0,codigo_externo TEXT,fantasia TEXT,endereco TEXT,situacao TEXT,tipo_pessoa TEXT,inscricao_estadual TEXT)",
   "CREATE TABLE fornecedores(id INTEGER PRIMARY KEY AUTOINCREMENT,nome TEXT,documento TEXT,telefone TEXT,email TEXT,codigo_externo TEXT,razao_social TEXT,inscricao_estadual TEXT,situacao TEXT,cep TEXT,estado TEXT,cidade TEXT,endereco TEXT,numero TEXT,bairro TEXT,complemento TEXT,origem_importacao TEXT)",
   "CREATE TABLE produtos(id INTEGER PRIMARY KEY AUTOINCREMENT,codigo TEXT,descricao TEXT,ncm TEXT,unidade TEXT,custo REAL DEFAULT 0,preco REAL DEFAULT 0,estoque REAL DEFAULT 0,codigo_barras TEXT,tipo_produto TEXT,ncm_status TEXT,ncm_observacao TEXT)",
   "CREATE TABLE bancos(id INTEGER PRIMARY KEY AUTOINCREMENT,nome TEXT,agencia TEXT,conta TEXT,tipo TEXT,saldo_inicial REAL DEFAULT 0,saldo REAL DEFAULT 0,ativo INTEGER DEFAULT 1,observacao TEXT)",
   "CREATE TABLE plano_contas(id INTEGER PRIMARY KEY AUTOINCREMENT,codigo TEXT,nome TEXT,natureza TEXT,grupo TEXT,classificacao TEXT,ativo INTEGER DEFAULT 1)",
   "CREATE TABLE centros_custo(id INTEGER PRIMARY KEY AUTOINCREMENT,codigo TEXT,descricao TEXT,ativo INTEGER DEFAULT 1,observacao TEXT)",
   "CREATE TABLE titulos(id INTEGER PRIMARY KEY AUTOINCREMENT,tipo TEXT,pessoa TEXT,documento TEXT,emissao TEXT,vencimento TEXT,valor REAL DEFAULT 0,valor_baixado REAL DEFAULT 0,status TEXT DEFAULT 'Aberto',plano_id INTEGER,banco_id INTEGER,observacao TEXT,participante_id INTEGER,competencia TEXT,lancar_financeiro INTEGER DEFAULT 1,lancar_contabilidade INTEGER DEFAULT 1,data_baixa TEXT,origem TEXT,centro_custo_id INTEGER)",
   "CREATE TABLE boletos(id INTEGER PRIMARY KEY AUTOINCREMENT,codigo TEXT,cliente TEXT,emissao TEXT,banco TEXT,vencimento TEXT,valor REAL DEFAULT 0,juros REAL DEFAULT 0,multa REAL DEFAULT 0,desconto REAL DEFAULT 0,total REAL DEFAULT 0,pagamento TEXT,situacao TEXT,observacao TEXT,criado_em TEXT,cliente_id INTEGER,banco_id INTEGER,plano_id INTEGER,lancar_financeiro INTEGER,lancar_contabilidade INTEGER,centro_custo_id INTEGER)",
   "CREATE TABLE fiado(id INTEGER PRIMARY KEY AUTOINCREMENT,cliente_id INTEGER,data TEXT,tipo TEXT,descricao TEXT,valor REAL DEFAULT 0)",
   "CREATE TABLE cheques_juros(id INTEGER PRIMARY KEY AUTOINCREMENT,cliente_id INTEGER,cliente TEXT,documento TEXT,emissao TEXT,vencimento TEXT,inicio TEXT,final TEXT,dias INTEGER,valor REAL,taxa REAL,juros REAL,total REAL,integrado INTEGER DEFAULT 0)",
   "CREATE TABLE cheques_desconto(id INTEGER PRIMARY KEY AUTOINCREMENT,cliente_id INTEGER,cliente TEXT,documento TEXT,emissao TEXT,vencimento TEXT,inicio TEXT,final TEXT,dias INTEGER,valor REAL,taxa REAL,juros REAL,total REAL,integrado INTEGER DEFAULT 0)",
   "CREATE TABLE vendas(id INTEGER PRIMARY KEY AUTOINCREMENT,numero TEXT,data TEXT,cliente_id INTEGER,cliente_nome TEXT,plano_id INTEGER,centro_custo_id INTEGER,banco_id INTEGER,condicao TEXT,vencimento TEXT,subtotal REAL,desconto REAL,total REAL,observacao TEXT,criado_em TEXT)",
   "CREATE TABLE venda_itens(id INTEGER PRIMARY KEY AUTOINCREMENT,venda_id INTEGER,produto_id INTEGER,codigo TEXT,descricao TEXT,quantidade REAL,preco REAL,desconto REAL,total REAL)",
   "CREATE TABLE estoque_movimentos(id INTEGER PRIMARY KEY AUTOINCREMENT,produto_id INTEGER,data TEXT,tipo TEXT,quantidade REAL,custo_unitario REAL,origem TEXT,documento TEXT,observacao TEXT,referencia_id INTEGER)",
   "CREATE TABLE movimentos_contas_financeiras(id INTEGER PRIMARY KEY AUTOINCREMENT,conta_id INTEGER,data TEXT,tipo TEXT,valor REAL,documento TEXT,historico TEXT,plano_conta_id INTEGER,centro_custo_id INTEGER,origem TEXT,referencia_id INTEGER,criado_em TEXT)",
   "CREATE TABLE transferencias(id INTEGER PRIMARY KEY AUTOINCREMENT,data TEXT,banco_origem TEXT,banco_destino TEXT,valor REAL,descricao TEXT,origem_tipo TEXT,destino_tipo TEXT,origem_conta_id INTEGER,destino_conta_id INTEGER,documento TEXT)",
   "CREATE TABLE importacoes_lancamentos(id INTEGER PRIMARY KEY AUTOINCREMENT,uuid TEXT UNIQUE,tipo TEXT,origem_arquivo TEXT,status TEXT,detalhe TEXT,importado_em TEXT)"
  }; for(String s:sql)d.execSQL(s);
 }
 public void onUpgrade(SQLiteDatabase d,int a,int b){}
 long count(String t){Cursor c=getReadableDatabase().rawQuery("SELECT count(*) FROM "+t,null);c.moveToFirst();long n=c.getLong(0);c.close();return n;}
 double scalar(String sql){Cursor c=getReadableDatabase().rawQuery(sql,null);double n=c.moveToFirst()?c.getDouble(0):0;c.close();return n;}
 void replaceDatabase(InputStream in)throws Exception{close();File f=ctx.getDatabasePath(NAME);File tmp=new File(f.getParent(),NAME+".novo");try(FileOutputStream o=new FileOutputStream(tmp)){byte[]b=new byte[65536];int n;while((n=in.read(b))>0)o.write(b,0,n);}SQLiteDatabase test=SQLiteDatabase.openDatabase(tmp.getPath(),null,SQLiteDatabase.OPEN_READONLY);Cursor c=test.rawQuery("PRAGMA integrity_check",null);if(!c.moveToFirst()||!"ok".equalsIgnoreCase(c.getString(0)))throw new IOException("Banco SQLite inválido");c.close();test.close();if(f.exists())new File(f.getPath()+".bak").delete();if(f.exists())f.renameTo(new File(f.getPath()+".bak"));if(!tmp.renameTo(f))throw new IOException("Não foi possível ativar o banco");}
}
