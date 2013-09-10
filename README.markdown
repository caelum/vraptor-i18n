## vraptor-i18n

Uma biblioteca simples, compatível com o jstl fmt:message para i18n nas views.

# instalação

É possível fazer o download do vraptor-i18n.jar do repositório do Maven, ou configurado em qualquer ferramenta compatível:

		<dependency>
			<groupId>br.com.caelum.vraptor</groupId>
			<artifactId>vraptor-i18n</artifactId>
			<version>1.0.0</version>
			<scope>compile</scope>
		</dependency>


# Acessando mensagens com jsp

Acesse as mensagens internacionalizadas com:

		<h1>${t['meu_sistema']}</h1>

E em seu arquivo messages.properties:

		meu_sistema = Bem vindo ao meu sistema
		
O resultado será:

		<h1>Bem vindo ao meu sistema</h1>
	
# Mensagens não encontradas
		
Caso a mensagem não tenha sido traduzida, o resultado será:

		<h1><span class="i18n_missing_key">meu_sistema</span></h1>
	
Dessa maneira você pode fazer seus testes de integração procurarem por "i18n_missing_keys" em todas as telas que passam e quebrar o teste caso alguma passou para trás sem tradução.

# Parametros

Você pode passar parâmetros para as mensagens da seguinte maneira:

		<h1>${t['deve_ser_preenchido'].args('nome','Usuário')}</h1>

Com o arquivo de mensagens:

		deve_ser_preenchido = O campo {0} deve ser preenchido para todo {1} novo.
	
E o resultado será:

		O campo nome deve ser preenchido para todo Usuário novo.

# Ajuda

Envie perguntas no forum de perguntas e respostas do http://www.guj.com.br