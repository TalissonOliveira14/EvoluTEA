all: compile

compile:
	@mkdir -p bin
	@find src -name "*.java" | xargs javac -d bin

run:
	java -cp bin src.view.MenuPrincipal

clean:
	@rm -rf bin