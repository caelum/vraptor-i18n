## vraptor-i18n

Uma biblioteca simples, compatível com o jstl fmt:message para i18n nas views.

# instalação e requerimentos

É possível fazer o download do vraptor-i18n.jar do repositório do Maven, ou configurado em qualquer ferramenta compatível:

		<dependency>
			<groupId>br.com.caelum.vraptor</groupId>
			<artifactId>vraptor-i18n</artifactId>
			<version>1.0.0</version>
			<scope>compile</scope>
		</dependency>


O vraptor-i18n requer servlet api 3.0

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

# Migrando

As duas regex a seguir ajudam a transformar seus jsps e tagfiles em uma batida só:

		<fmt:message key="([^"]+)"/> ==> ${t['$1']}
		<fmt:message key="([^"]+)"></fmt:message> ==> ${t['$1']}
		
Contribua com uma regex para o caso que usa parâmetros

<<<<<<< HEAD
# Formatando datas

É possível formatar datas da seguinte forma:

    ${l[minhaData]}

Ou ainda passar formatos suportados pelo DateFormat:

    ${l[minhaData].format('full')}
    ${l[minhaData].format('short')}

Ou pelo SimpleDateFormat:

    ${l[minhaData].custom('dd/MM/yyyy hh:mm:ss')}

Caso não seja passado nenhum formato personalizado, é usado o formato padrão
DateFormat.DEFAULT

# Tipos de data suportados

O l suporta além Date, Calendar e as datas do JodaTime. Nada de ficar dando getters loucos de um lado pro outro.

# Release

	mvn release:prepare
	mvn release:perform
	open http://oss.sonatype.org

# Ajuda

Envie perguntas no forum de perguntas e respostas do http://www.guj.com.br
