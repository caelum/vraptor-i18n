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

# Pluralization/Count

A internacionalização permite que você suporte mensagens com plural ou count de elementos.
Dadas as mensagens:

	messages_found.zero = No messages found
	messages_found.one = One message found
	messages_found.other = {0} messages found
	
Você pode invocar:

	${t['messages_found'].count(messages.size())}
	
Caso o valor passado para count seja 0, ele usa a mensagem zero. Caso 1, ele chama one, caso diferente desses valores, ele chama other.
Nos três casos o parâmetro {0} é o número de elementos.

# Formatando datas

É possível formatar datas da seguinte forma:

    ${l[minhaData]}

Ou ainda passar formatos suportados pelo DateFormat:

    ${l[minhaData].format('full')}
    ${l[minhaData].format('short')}

Ou pelo SimpleDateFormat:

    ${l[minhaData].pattern('dd/MM/yyyy hh:mm:ss')}


Se quiser parametrizar a formatação da data no arquivo messages.properties,
também é possível. Neste caso é só fazer o seguinte:

Configurar a chave desejada no arquivo messages.properties. A chave deve sempre
começar com "time.formats":

    time.formats.pirate = yyyy.MM.dd G 'at' HH:mm:ss z

Utilizar a formatação na view:

    ${l[minhaData].custom('pirate')}

Caso não seja passado nenhum formato personalizado, é usado o formato padrão
DateFormat.DEFAULT

# Tipos de data suportados

O l suporta além Date, Calendar e as datas do JodaTime. Nada de ficar dando getters loucos de um lado pro outro.

# i18n Filter: usando o vraptor-i18n em páginas que não foram acessadas através do vRaptor

Para usar o i18nfilter basta configurá-lo no web.xml. O nome do filtro é "vraptor-i18n" e você fica livre para configurar
os padrões de URI. Por exemplo, para suportar as páginas de erro:

	<!-- jamais mudar o filter-name -->
	<filter-mapping>
		<filter-name>vraptor-i18n</filter-name>
		<url-pattern>/WEB-INF/jsp/error/*</url-pattern>
	</filter-mapping>

	<error-page>
		<error-code>404</error-code>
		<location>/WEB-INF/jsp/error/404.jsp</location>
	</error-page>	

# Release

	mvn release:prepare
	mvn release:perform
	open http://oss.sonatype.org

# Ajuda

Envie perguntas no forum de perguntas e respostas do http://www.guj.com.br
