package binnie.genetics.machine;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachine extends ModelBase {
   ModelRenderer Base;
   ModelRenderer Body;
   ModelRenderer Arm1;
   ModelRenderer Arm2;
   ModelRenderer Arm3;
   ModelRenderer Arm4;
   ModelRenderer Top;

   public ModelMachine() {
      super();
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.Base = new ModelRenderer(this, 0, 18);
      this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 4, 16);
      this.Base.setRotationPoint(-8.0F, 20.0F, -8.0F);
      this.Base.setTextureSize(64, 64);
      this.Base.mirror = true;
      this.setRotation(this.Base, 0.0F, 0.0F, 0.0F);
      this.Body = new ModelRenderer(this, 0, 26);
      this.Body.addBox(0.0F, 0.0F, 0.0F, 12, 7, 12);
      this.Body.setRotationPoint(-6.0F, 13.0F, -6.0F);
      this.Body.setTextureSize(64, 64);
      this.Body.mirror = true;
      this.setRotation(this.Body, 0.0F, 0.0F, 0.0F);
      this.Arm1 = new ModelRenderer(this, 0, 0);
      this.Arm1.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3);
      this.Arm1.setRotationPoint(5.0F, 8.0F, 5.0F);
      this.Arm1.setTextureSize(64, 64);
      this.Arm1.mirror = true;
      this.setRotation(this.Arm1, 0.0F, 0.0F, 0.0F);
      this.Arm2 = new ModelRenderer(this, 0, 0);
      this.Arm2.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3);
      this.Arm2.setRotationPoint(-5.0F, 8.0F, 5.0F);
      this.Arm2.setTextureSize(64, 64);
      this.Arm2.mirror = true;
      this.setRotation(this.Arm2, 0.0F, -1.57075F, 0.0F);
      this.Arm3 = new ModelRenderer(this, 0, 0);
      this.Arm3.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3);
      this.Arm3.setRotationPoint(-5.0F, 8.0F, -5.0F);
      this.Arm3.setTextureSize(64, 64);
      this.Arm3.mirror = true;
      this.setRotation(this.Arm3, 0.0F, -3.1415F, 0.0F);
      this.Arm4 = new ModelRenderer(this, 0, 0);
      this.Arm4.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3);
      this.Arm4.setRotationPoint(5.0F, 8.0F, -5.0F);
      this.Arm4.setTextureSize(64, 64);
      this.Arm4.mirror = true;
      this.setRotation(this.Arm4, 0.0F, -4.7122498F, 0.0F);
      this.Top = new ModelRenderer(this, 0, 0);
      this.Top.addBox(0.0F, 0.0F, 0.0F, 14, 4, 14);
      this.Top.setRotationPoint(-7.0F, 9.0F, -7.0F);
      this.Top.setTextureSize(64, 64);
      this.Top.mirror = true;
      this.setRotation(this.Top, 0.0F, 0.0F, 0.0F);
   }

   public void render(float f, float f1, float f2, float f3, float f4, float f5) {
      super.render((Entity)null, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5);
      this.Base.render(f5);
      this.Body.render(f5);
      this.Arm1.render(f5);
      this.Arm2.render(f5);
      this.Arm3.render(f5);
      this.Arm4.render(f5);
      this.Top.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, (Entity)null);
   }
}
