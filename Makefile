all: compile

compile:
	mkdir -p bin
	javac -d bin src/**/*.java

run:
	java -cp bin src.view.MenuPrincipal

clean:
	rm -rf bin/*

help:
	@echo "Comandos disponíveis:"
	@echo "  make         - Compila o projeto"
	@echo "  make run     - Executa o sistema"
	@echo "  make clean   - Limpa os arquivos compilados"