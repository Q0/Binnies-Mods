package binnie.core.language;

import binnie.core.AbstractMod;
import binnie.core.ManagerBase;
import net.minecraft.util.StatCollector;

import java.util.HashMap;
import java.util.Map;

public class ManagerLanguage extends ManagerBase {
    private Map objNames = new HashMap();

    public ManagerLanguage() {
        super();
    }

    public void addObjectName(Object obj, String name) {
        this.objNames.put(obj, name);
    }

    public String unlocalised(AbstractMod mod, String id) {
        return mod.getModID() + "." + id;
    }

    public String localise(Object key) {
        String loc = StatCollector.translateToLocal(key.toString());
        return loc.equals(key.toString()) ? (this.objNames.containsKey(key) ? this.localise(this.objNames.get(key)) : key.toString()) : loc;
    }

    public String localise(AbstractMod mod, String id) {
        return this.localise(this.unlocalised(mod, id));
    }

    public String localiseOrBlank(AbstractMod mod, String id) {
        return this.localiseOrBlank(this.unlocalised(mod, id));
    }

    public String localise(AbstractMod mod, String id, Object... objs) {
        return String.format(this.localise(mod, id), objs);
    }

    public String localiseOrBlank(Object key) {
        String trans = this.localise(key);
        return trans.equals(key) ? "" : trans;
    }

    public boolean canLocalise(Object key) {
        String trans = this.localise(key);
        return !trans.equals(key);
    }
}
