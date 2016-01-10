package binnie.core.machines.power;

import binnie.core.machines.power.ErrorState;

public interface IErrorStateSource {
   ErrorState canWork();

   ErrorState canProgress();
}
