package binnie.extratrees.alcohol;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AlcoholEffect {
   public AlcoholEffect() {
      super();
   }

   public static void makeDrunk(EntityPlayer player, float strength) {
      int existingStrength = player.isPotionActive(Potion.confusion)?player.getActivePotionEffect(Potion.confusion).getAmplifier():0;
      int existingTime = player.isPotionActive(Potion.confusion)?player.getActivePotionEffect(Potion.confusion).getDuration():0;
      int time = (int)(100.0D * Math.sqrt((double)strength)) + existingTime;
      float intensity = 0.1F * strength + (float)existingStrength + (float)(existingTime / 500);
      if(time < 5) {
         time = 5;
      }

      float slowIntense = (intensity - 10.0F) / 4.0F;
      if(slowIntense < 0.0F) {
         slowIntense = 0.0F;
      }

      float blindIntense = (intensity - 25.0F) / 2.0F;
      if(blindIntense < 0.0F) {
         blindIntense = 0.0F;
      }

      player.addPotionEffect(new PotionEffect(Potion.confusion.id, time, (int)intensity, false));
      if(slowIntense > 0.0F) {
         player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, time, (int)slowIntense, false));
      }

   }
}
