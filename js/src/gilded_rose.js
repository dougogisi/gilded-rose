const TYPE_NECROMANTIC = 'necromantic';
const TYPE_LEGENDARY = 'legendary';
const TYPE_CONJURED = 'conjured';
const TYPE_DEFAULT = 'default';
const TYPE_CONCERT = 'concert'

const QUALITY_CAP = 50;

/**
 * 
 * @param {String} name 
 * @param {Number} sell_in 
 * @param {Number} quality 
 */
function Item(name, sell_in, quality) {
  this.name = name;
  this.sell_in = sell_in;
  this.quality = quality;
}

var items = []

function update_quality() {
  for (var i = 0; i < items.length; i++) {
    let item = items[i];

    // Mutators are a set of functions that know how to change an items state
    // return array
    let stateMutators = getStateMutators();
    let next = applyStateMutators(item, stateMutators);

    item.sell_in = next.sell_in;
    item.quality = next.quality;
  }
}

/**
 * Returns array of state functions
 */
function getStateMutators() {
  return [
    qualityEffectFunc,
    sellInEffectFunc
    // more state functions
  ]
}

/**
 * can be broken down into smaller effect funcs
 * @param {Item} item 
 * @param {Function} typeDescriptor determines the Item type
 */
const qualityEffectFunc = function (item) {
  let quality_modifier = 1;
  let next = Object.assign({}, item);
  let item_type = "";
  const sanitizedName = item.name.replace(/\s+/g, '').toLowerCase();

  if (sanitizedName.startsWith('agedbrie')) {
    item_type = TYPE_NECROMANTIC;
  }

  if (sanitizedName.startsWith('sulfuras')) {
    item_type = TYPE_LEGENDARY;
  }

  if (sanitizedName.startsWith('conjured')) {
    item_type = TYPE_CONJURED;
  }

  if (sanitizedName.startsWith('backstagepasses')) {
    item_type = TYPE_CONCERT;
  }

  if (item_type === "") {
    item_type = TYPE_DEFAULT;
  }

  next.history = {
    prev: {
      sell_in: item.sell_in,
      quality: item.quality
    }
  };

  switch (item_type) {
    case TYPE_LEGENDARY:
      break;
    case TYPE_CONCERT:
      // this block runs for concert type only
      if (next.sell_in < 6) {
        quality_modifier = 3;
      } else if (next.sell_in < 11) {
        quality_modifier = 2;
      }

    case TYPE_NECROMANTIC:
      // this block runs for both necronmantic and concert type
      next.quality = next.quality + quality_modifier > QUALITY_CAP ? QUALITY_CAP : next.quality + quality_modifier;

      if (next.sell_in <= 0) {
        next.quality = 0;
      }
      break;
    case TYPE_CONJURED:
      // quality drop doubled for conjured items and quadripled after sell_in date reaches 0
      quality_modifier = next.sell_in <= 0 ? (quality_modifier+quality_modifier) * 2 : quality_modifier+quality_modifier;
      next.quality = next.quality - quality_modifier;
      break;
    case TYPE_DEFAULT:
    default:
      // quality drop doubled after sell_in date reaches 0
      quality_modifier = next.sell_in <= 0 ? quality_modifier+quality_modifier : quality_modifier;
      next.quality = next.quality - quality_modifier;
  }

  next.quality = Math.max(0, next.quality);

  return next;
}

const sellInEffectFunc = function (item) {
  let next = Object.assign({}, item);
  const sanitizedName = item.name.replace(/\s+/g, '').toLowerCase();

  if (!sanitizedName.startsWith('sulfuras')) {
    next.sell_in = next.sell_in - 1;
  }

  return next;
}

/**
 * 
 * @param {Item} prev 
 * @param {Array} stateMutators 
 */
function applyStateMutators(prev, stateMutators) {
  let next = Object.assign({}, prev);

  stateMutators.forEach( function(stateMutator) {
    next = stateMutator(next);
  });

  return next;
}
