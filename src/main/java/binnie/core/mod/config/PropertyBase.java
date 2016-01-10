package binnie.core.mod.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

abstract class PropertyBase {
    Configuration file;
    Property property;
    Object defaultValue;
    private ConfigProperty configProperty;
    Annotation annotatedProperty;
    private List comments = new ArrayList();
    private Field field;

    protected PropertyBase(Field field, BinnieConfiguration file, ConfigProperty configProperty, Annotation annotedProperty) throws IllegalArgumentException, IllegalAccessException {
        super();
        this.field = field;
        this.file = file;
        this.configProperty = configProperty;
        this.annotatedProperty = annotedProperty;
        this.defaultValue = this.getDefaultValue(field);
        this.property = this.getProperty();

        for (String comment : configProperty.comment()) {
            this.addComment(comment);
        }

        this.addComments();
        this.property.comment = this.getComment();
        field.set((Object) null, this.getConfigValue());
    }

    protected abstract Property getProperty();

    protected abstract Object getConfigValue();

    protected abstract void addComments();

    protected String getCategory() {
        return this.configProperty.category().equals("") ? ((ConfigProperty.Type) this.annotatedProperty.annotationType().getAnnotation(ConfigProperty.Type.class)).category() : this.configProperty.category();
    }

    protected String getKey() {
        return this.configProperty.key();
    }

    protected Object getDefaultValue(Field field) throws IllegalArgumentException, IllegalAccessException {
        return field.get((Object) null);
    }

    protected void addComment(String comment) {
        this.comments.add(comment);
    }

    protected String getComment() {
        String comment = "";

        for (String com : this.comments) {
            comment = comment + com + " ";
        }

        return comment;
    }
}
