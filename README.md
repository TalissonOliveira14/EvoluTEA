# 🏥 EvoluTEA - Sistema de Gestão de Clínica TEA

## 📖 Visão Geral
O **EvoluTEA** é um sistema de software desenvolvido para otimizar o fluxo de atendimento em clínicas de terapia voltadas ao tratamento do Transtorno do Espectro Autista (TEA). O sistema foca em garantir a rastreabilidade clínica através de uma máquina de estados robusta e gestão de dados orientada a objetos.

## 🏛️ Modelagem do Sistema
O projeto foi modelado seguindo os princípios de POO (SOLID), garantindo baixo acoplamento e alta coesão.

### Diagrama de Classes
*(Aqui, se você tiver o diagrama, coloque a imagem no repositório, por exemplo, em uma pasta `/docs/diagrama.png` e use a tag abaixo)*
![Diagrama de Classes do EvoluTEA](./docs/diagrama.png)

*O diagrama acima ilustra as relações entre as entidades `Paciente`, `Responsavel`, `Profissional` e `Sessao`, destacando a implementação da interface `CalculadorHonorario` que viabiliza o polimorfismo no repasse financeiro.*

## ⚙️ Arquitetura e Regras de Negócio
1. **Máquina de Estados:** As transições de `Sessao` são protegidas por lógica de negócio na classe `Sessao.java`. Tentar transições inválidas dispara uma exceção personalizada (`EstadoInvalidoException`).
2. **Polimorfismo:** O cálculo de honorários é tratado polimorficamente via interface `CalculadorHonorario`, permitindo adicionar novas regras de remuneração (Ex: planos de saúde distintos) sem alterar a estrutura principal.
3. **Persistência:** Utilização de File I/O para persistência de dados em formato CSV (`sessoes.txt`, etc.).

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java (JDK 11+)
* **Paradigma:** Orientação a Objetos (POO)
* **Controle de Versão:** Git/GitHub

## 🚀 Como Executar
1. **Clonar:** `git clone https://github.com/TalissonOliveira14/EvoluTEA.git`
2. **Compilar:** `javac -d bin src/Main.java src/exception/*.java src/view/MenuPrincipal.java src/model/*.java src/repository/*.java`
3. **Executar:** `java -cp bin src.Main`

## 👥 Integrantes
* Talisson Oliveira
* Allson [Sobrenome]

---
*Projeto desenvolvido para a Atividade da Unidade 03 - Professor Jefferson Gomes Dutra.*
