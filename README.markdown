## vraptor-i18n


Uma biblioteca simples, compatível com o jstl fmt:message para i18n nas views.

# instalação e requerimentos

É possível fazer o download do vraptor-i18n.jar do repositório do Maven, ou configurado em qualquer ferramenta compatível:

		<dependency>
			<groupId>br.com.caelum.vraptor</groupId>
			<artifactId>vraptor-i18n</artifactId>
			<version>4.0.1</version> <!-- or latest version -->
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
começar com "formats.time":

    formats.time.pirate = yyyy.MM.dd G 'at' HH:mm:ss z

Utilizar a formatação na view:

    ${l[minhaData].custom('pirate')}

Caso não seja passado nenhum formato personalizado, é usado o formato padrão
DateFormat.DEFAULT

# Números

Da mesma maneira que datas, você pode formatar números com:

	${l[meuNumero]}

Similarmente você pode usar padrões do DecimalFormat:

	${l[meuNumero].pattern("#,##0.00")}
	
E dar nomes a seus padrões:

	${l[meuNumero].custom("percentage")}
	
Colocando então no messages.properties:

	formats.number.percentage = ##0.00%

# Tipos de data e números suportados

O l suporta além Date, Calendar e as datas do JodaTime. Nada de ficar dando getters loucos de um lado pro outro.
Suportamos qualquer tipo de número que implementa Number (incluindo números primitivos, seus wrappers e BigInteger/BigDecimal).

# Páginas de erro

Se sua página de erro está dentro de qualquer subdiretório de /WEB-INF/, ela automaticamente passará por um filtro que registra tanto t quanto l.

# Alterando o locale padrão

Para que o plugin mude o idioma configurado para a sessão do usuário, basta adicionar no request o parâmetro <code>_locale</code>. Por exemplo, <code>http://seudominio.com/seupath?_locale=pt_BR</code> configurará o locale para Português do Brasil. O parâmetro pode ser enviado via POST também.

# URL com locale

Além das URIs padrão do VRaptor o plugin aceita URIs com o locale no início, por exemplo: 
```
/controller/action
/en-us/controller/action
``` 

# Tradução de rotas

As rotas podem ser traduzidas adicionando um arquivo de propriedades para o locale com o nome <code>routes_{locale}.properties</code>.
Para traduzir adicione as rotas com suas traduções no arquivo no formato chave/valor.

Exemplo de tradução para português brasileiro do locale padrão em inglês:

*Conteúdo do arquivo <code>routes_pt_BR.properties</code>*:

```
/controller/action/{param} = /controlador/acao/{param}
```

*Rotas que respondem a este controller/action*:

```
/controller/action/{param}
/en-us/controller/action/{param}
/pt-br/controlador/acao/{param}
``` 

Nota: Ter URLs diferentes para o mesmo conteúdo pode trazer efeitos negativos para SEO, então traduza apenas URLs que também 
possuam o conteúdo traduzido.

# Release

	mvn release:prepare
	mvn release:perform
	open http://oss.sonatype.org

# Ajuda

Envie perguntas no forum de perguntas e respostas do http://www.guj.com.br
