# Kmeans
<h3>Kmeans feito em Java pela matéria de inteligência artificial pelo 6º termo de ciência da computação</h3>
<hr>
<ul>
  <li>
    O projeto utiliza interface gráfica construida através do <a href="https://gluonhq.com/products/scene-builder/">Scene Builder.</a>  </li>
  <li>
    Também foi utilizada a biblioteca <a href="https://github.com/jfoenixadmin/JFoenix">JFoenix.</a>
  </li>
  <li>
    Possui um arquivo de teste.txt dentro do projeto e a possibilidade livre escolha de centróides e dimensões, para menipular este projeto. 
  </li>
  
  <hr>
  <p><small>O trabalho deve possibilitar ao usuário escolher a quantidade de centróides, realizar os agrupamentos com k-means e exibir os resultados em gráficos 2D, com possibilidade de configuração dos eixos para visualização, por exemplo, exibir o atributo petal width no eixo X e o atributo sepal length no eixo Y. Deve rotular os pontos classificados e indicar os acertos e erros da técnica.</small></p>
  
  <h3>O algoritmo:</h3>

<p>
A ideia do algoritmo K-Means (também chamado de K-Médias) é fornecer uma classificação de informações de acordo com os próprios dados. Esta classificação, como será vista a seguir, é baseada em análise e comparações entre os valores numéricos dos dados. Dessa maneira, o algoritmo automaticamente vai fornecer uma classificação automática sem a necessidade de nenhuma supervisão humana, ou seja, sem nenhuma pré-classificação existente. Por causa dessa característica, o K-Means é considerado como um algoritmo de mineração de dados não supervisionado.
</p>

<p>
Para entender como o algoritmo funciona, vamos imaginar que temos uma tabela com linhas e colunas que contêm os dados a serem classificados. Nesta tabela, cada coluna é chamada de dimensão e cada linha contém informações para cada dimensão, que também são chamadas de ocorrências ou pontos. Geralmente, trabalha-se com dados contínuos neste algoritmo, mas nada impede que dados discretos sejam utilizados, deste que eles sejam mapeados para valores numéricos correspondentes.
</p>

<p>
Como foi dito, o algoritmo vai analisar todos os dados desta tabela e criar classificações. Isto é, o algoritmo vai indicar uma classe (cluster) e vai dizer quais linhas pertencem a esta classe. O usuário deve fornecer ao algoritmo a quantidade de classes que ele deseja. Este número de classes que deve ser passada para o algoritmo é chamado de k e é daí que vem a primeira letra do algoritmo: K-Means.
</p>

<p>
Para gerar as classes e classificar as ocorrências, o algoritmo faz uma comparação entre cada valor de cada linha por meio da distância. Geralmente utiliza-se a distância euclidiana para calcular o quão ‘longe’ uma ocorrência está da outra. A maneira de calcular esta distância vai depender da quantidade de atributos da tabela fornecida. Após o cálculo das distâncias o algoritmo calcula centróides para cada uma das classes. Conforme o algoritmo vai iterando, o valor de cada centróide é refinado pela média dos valores de cada atributo de cada ocorrência que pertence a este centróide. Com isso, o algoritmo gera k centróides e coloca as ocorrências da tabela de acordo com sua distância dos centróides.
</p>

<p>
Para simplificar a explicação de como o algoritmo funciona vou apresentar o algoritmo K-Means em cinco passos:
</p>

<p><strong>PASSO 01: Fornecer valores para os centróides.</strong></p>

<p>
Neste passo os k centróides devem receber valores iniciais. No início do algoritmo geralmente escolhe-se os k primeiros pontos da tabela. Também é importante colocar todos os pontos em um centróide qualquer para que o algoritmo possa iniciar seu processamento.
<p>

<p><strong>PASSO 02: Gerar uma matriz de distância entre cada ponto e os centróides.</strong></p>
<p>
Neste passo, a distância entre cada ponto e os centróides é calculada. A parte mais ‘pesada’ de cálculos ocorre neste passo pois se temos N pontos e k centróides teremos que calcular  N x k distâncias neste passo. Cada centróide terá a mesma quantidade de atributos que o conjunto de dados. Por exemplo, considerando um conjunto de dados com 4 atributos (a, b, c, d), o cálculo da distância Euclidiana será da seguinte forma:
</p>

<em>DistEuclidiana=&radic;(p1.a−c.a)^2+(p1.b−c.b)^2+(p1.c−c.c)^2+(p1.d−c.d)^2</em>


<p><strong>PASSO 03: Colocar cada ponto nas classes de acordo com a sua distância do centróide da classe.</strong></p>

<p>
Aqui, os pontos são classificados de acordo com sua distância dos centróides de cada classe. A classificação funciona assim: o centróide que está mais perto deste ponto vai ‘incorporá-lo’, ou seja, o ponto vai pertencer à classe representada pelo centróide que está mais perto do ponto. É importante dizer que o algoritmo termina se nenhum ponto ‘mudar’ de classe, ou seja, se nenhum ponto for ‘incorporado’ a uma classe diferente da que ele estava antes deste passo.
</p>

<p><strong>PASSO 04: Calcular os novos centróides para cada classe.</strong></p>

<p>
Neste momento, os valores das coordenadas dos centróides são refinados. Para cada classe que possui mais de um ponto o novo valor dos centróides é calculado fazendo-se a média de cada atributo de todos os pontos que pertencem a esta classe.
</p>

<p><strong>PASSO 05: Repetir até a convergência.</strong></p>

<p>
O algoritmo volta para o PASSO 02 repetindo iterativamente o refinamento do cálculo das coordenadas dos centróides.
</p>

<p>
Notem que desta maneira teremos uma classificação que coloca cada ponto em apenas uma classe. Desta maneira dizemos que este algoritmo faz uma classificação hard (hard clustering) uma vez que cada ponto só pode ser classificado em uma classe. Outros algoritmos trabalham com o conceito de classificação soft onde existe uma métrica que diz o quão ‘dentro’ de cada classe o ponto está.
</p>

<em>(<a href = "https://imasters.com.br/artigo/4709/sql-server/data-mining-na-pratica-algoritmo-k-means?trace=1519021197&source=single">Fonte</a>)</em>
