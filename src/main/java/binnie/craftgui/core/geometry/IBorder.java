package binnie.craftgui.core.geometry;

public class IBorder {
    public static final IBorder ZERO = new IBorder(0.0F);
    float t;
    float b;
    float l;
    float r;

    public IBorder(float pad) {
        this(pad, pad, pad, pad);
    }

    public IBorder(float tb, float rl) {
        this(tb, rl, tb, rl);
    }

    public IBorder(float t, float rl, float b) {
        this(t, rl, b, rl);
    }

    public IBorder(float t, float r, float b, float l) {
        super();
        this.t = t;
        this.b = b;
        this.l = l;
        this.r = r;
    }

    public IBorder(Position edge, float n) {
        this(edge == Position.Top ? n : 0.0F, edge == Position.Right ? n : 0.0F, edge == Position.Bottom ? n : 0.0F, edge == Position.Left ? n : 0.0F);
    }

    public IBorder(IBorder padding) {
        this(padding.t(), padding.r(), padding.b(), padding.l());
    }

    public float t() {
        return this.t;
    }

    public float b() {
        return this.b;
    }

    public float l() {
        return this.l;
    }

    public float r() {
        return this.r;
    }

    public float t(float n) {
        this.t = n;
        return this.t;
    }

    public float b(float n) {
        this.b = n;
        return this.b;
    }

    public float l(float n) {
        this.l = n;
        return this.l;
    }

    public float r(float n) {
        this.r = n;
        return this.r;
    }

    public boolean isNonZero() {
        return this.t != 0.0F || this.r != 0.0F || this.l != 0.0F || this.r != 0.0F;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public IPoint tl() {
        return new IPoint(this.l(), this.t());
    }

    /**
     * @deprecated
     */
    @Deprecated
    public IPoint tr() {
        return new IPoint(this.r(), this.t());
    }

    /**
     * @deprecated
     */
    @Deprecated
    public IPoint bl() {
        return new IPoint(this.l(), this.b());
    }

    /**
     * @deprecated
     */
    @Deprecated
    public IPoint br() {
        return new IPoint(this.r(), this.b());
    }

    public IBorder add(IBorder o) {
        return new IBorder(this.t() + o.t(), this.r() + o.r(), this.b() + o.b(), this.l() + o.l());
    }

    public String toString() {
        return this.t() + "-" + this.r() + "-" + this.b() + "-" + this.l();
    }
}
