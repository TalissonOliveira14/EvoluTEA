all: compile

compile:
	mkdir -p bin
	find src -name "*.java" | xargs javac -d bin

run:
	java -cp bin src.view.MenuPrincipal

clean:
	rm -rf bin

help:
	@echo "Comandos disponíveis:"
	@echo "  make         - Compila o projeto"
	@echo "  make run     - Executa o sistema"
	@echo "  make clean   - Limpa os arquivos compilados"