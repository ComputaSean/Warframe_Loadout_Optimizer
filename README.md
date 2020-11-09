# Warframe Loadout Optimizer

Given a vector of Warframe ability stats, this program attempts to find the optimal loadout (combination of mods) yielding the closest match.
Mods have different ranks which when installed affect the stats of a loadout, and no duplicate installed mods are allowed in the same loadout.

A mod can be represented as an R^4 vector [ability duration, ability efficiency, ability range, ability strength], and a loadout's vector is just the vector sum of all of its installed mods.

Then more generally, this is a nearest neighbor calculator returning all combinations of vectors (mods) from a given set (all ability affecting mods in Warframe) whose collective sum (represented by the loadout vector) is closest in Euclidean distance to a target vector (desired loadout).
This search is subject to the restriction that some vectors cannot be included in the same combination (no duplicate installed mods of the same name).

The program accomplishes this search by precomputing all possible loadouts and storing them in a k-d tree. Loadouts with vectors closest to the target vector are then queriable in O(log n) time. 

### Usage: ###

Input desired ability duration, efficiency, range, and strength in that order as an R^4 vector. \
To exit enter nothing.

### Limitations: ###

Only loadouts of up to 4 installed mods will be considered. The number of possible loadouts explodes combinatorially and can't be stored in memory as the installed mod limit increases. For reference there are more than 6 million loadouts with an installed mod limit of at most 4, and Warframes can have up to 8 mods installed.
