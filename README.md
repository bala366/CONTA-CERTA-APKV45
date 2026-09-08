# CONTA CERTA Android V045 MASTER

Projeto Android reconstruído a partir da estrutura real do CONTA_CERTA_V045.py, do banco SQLite V045 e das telas de referência do aplicativo.

## Identidade visual
- CONTA CERTA / Serviços Administrativos
- Azul-marinho e dourado
- LOGOMARCA original
- Dashboard em cartões de duas colunas
- Menu lateral com fonte 16sp para evitar cortes em celulares

## Banco de dados
Por segurança, o banco ativo real do computador NÃO é publicado no GitHub. O APK cria uma base compatível e possui no menu **Importar base V045**. Use uma CÓPIA do `conta_certa.db` do computador para carregar clientes, fornecedores, produtos e demais tabelas no aparelho.

## Integração com desktop
O menu **Exportar p/ computador** gera JSON no formato `CONTA_CERTA_LANCAMENTOS_V1`, compatível com a ponte existente na V045 desktop.

## Compilar
GitHub > Actions > Compilar APK Conta Certa V045 MASTER > Run workflow.
O artefato final chama-se `Conta_Certa_V045_MASTER.apk`.

## Atenção à atualização do APK antigo
O applicationId deste projeto é `br.com.contacerta.servicos`. Como o package ID e a chave de assinatura do APK antigo não foram recuperados, não há garantia de atualização por cima da instalação antiga. Não desinstale o APK antigo antes de preservar os dados dele.
