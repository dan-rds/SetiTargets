JFLAGS = -g 
JC = javac
JAVASRC = SetiTargets.java Target.java Time.java Position.java

MAINCLASS = SetiTargets
JARFILE = SetiTargets.jar

.SUFFIXES: .java .class
	$(JC) $(JFLAGS) $*.java

CLASSES = \
SetiTargets.class\
Target.class\
Time.class\
Position.class


all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(subst $$,\$$,$(CLASSES))
	rm Manifest

$(subst $$,\$$,$(CLASSES)): $(JAVASRC)
	$(JC) $(JFLAGS) $(JAVASRC)

clean:
	rm -f *.class setiTargets.jar

