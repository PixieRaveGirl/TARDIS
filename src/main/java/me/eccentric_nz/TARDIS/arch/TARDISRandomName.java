/*
 * Copyright (C) 2016 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.TARDIS.arch;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author eccentric_nz
 */
public class TARDISRandomName {

    private static final List<String> FIRSTNAMES = Arrays.asList("able", "absolute", "absurd", "academic", "accurate", "aching", "acidic", "active", "actual", "adept", "admired", "adored", "adroit", "advanced", "affable", "afraid", "aged", "agile", "agitated", "agnostic", "ajar", "alarmed", "alarming", "alert", "alive", "amazing", "amiable", "amicable", "ample", "amused", "amusing", "anchored", "ancient", "angelic", "angry", "animated", "annual", "another", "antique", "anxious", "apt", "arctic", "arid", "aromatic", "arrogant", "artistic", "artless", "ashamed", "assured", "astute", "athletic", "attached", "austere", "average", "aware", "awesome", "awful", "awkward", "babyish", "back", "bad", "baggy", "bare", "barren", "base", "basic", "belated", "beloved", "best", "better", "big", "bigoted", "bitter", "bizarre", "black", "bland", "blank", "blaring", "bleak", "blind", "blissful", "blond", "blue", "blushing", "boastful", "bogus", "boiling", "bold", "bony", "boorish", "boring", "bossy", "both", "bouncy", "bowed", "brave", "brawny", "brief", "bright", "brisk", "broken", "bronze", "brown", "bruised", "brusque", "brutish", "bubbly", "bulky", "bumpy", "bungling", "buoyant", "burly", "bustling", "busy", "buttery", "buzzing", "callous", "calm", "candid", "canine", "capable", "capital", "captious", "carefree", "careful", "careless", "caring", "carnal", "caustic", "cautious", "charming", "chaste", "cheap", "cheerful", "cheery", "chief", "chilly", "chubby", "churlish", "circular", "civil", "classic", "clean", "clear", "clever", "close", "closed", "cloudy", "clueless", "clumsy", "coarse", "cold", "colorful", "colossal", "comely", "common", "complete", "complex", "composed", "concrete", "confused", "constant", "content", "cooked", "cool", "cordial", "corny", "corrupt", "costly", "cowardly", "coy", "crabbed", "crafty", "crass", "craven", "crazy", "creamy", "creative", "credible", "creepy", "criminal", "crisp", "critical", "crooked", "crowded", "cruel", "crushing", "crusty", "cuddly", "cultural", "cultured", "cunning", "curly", "curvy", "cute", "cynical", "dainty", "damaged", "damp", "dapper", "daring", "dark", "darling", "dazzling", "dead", "deadly", "dear", "dearest", "debonair", "decent", "decimal", "decisive", "decrepit", "deep", "defiant", "definite", "delayed", "delicate", "demure", "dense", "dental", "depraved", "deserted", "detailed", "devoted", "devout", "diabolic", "didactic", "digital", "dilatory", "diligent", "dim", "dimpled", "direct", "dirty", "discreet", "discrete", "disloyal", "distinct", "dizzy", "dogmatic", "dopey", "doting", "double", "drab", "drafty", "dramatic", "droopy", "dry", "dual", "dull", "dutiful", "each", "eager", "early", "earnest", "easy", "ecstatic", "edible", "educated", "elastic", "elated", "elderly", "electric", "elegant", "eloquent", "eminent", "empty", "enormous", "enraged", "entire", "envious", "equal", "erratic", "erudite", "esoteric", "esteemed", "ethical", "euphoric", "even", "every", "evil", "exalted", "excited", "exciting", "exotic", "expert", "fabulous", "failing", "faint", "fair", "faithful", "fake", "false", "familiar", "famous", "fanciful", "fancy", "far", "far-off", "faraway", "fast", "fat", "fatal", "fatherly", "fatuous", "favorite", "fawning", "fearful", "fearless", "feeble", "feisty", "feline", "female", "feminine", "few", "fickle", "fiend", "filthy", "fine", "finished", "firm", "first", "fitting", "fixed", "flaky", "flashy", "flat", "flawed", "flawless", "flimsy", "flippant", "flowery", "fluffy", "fluid", "focused", "fond", "foolish", "forceful", "forked", "formal", "forsaken", "foul", "fragrant", "frail", "frank", "frayed", "free", "French", "frequent", "fresh", "friendly", "frigid", "frilly", "frizzy", "front", "frosty", "frozen", "frugal", "fruitful", "full", "fumbling", "funny", "fussy", "fuzzy", "gaseous", "gawky", "general", "generous", "genial", "genteel", "gentle", "genuine", "ghastly", "giant", "giddy", "gifted", "gigantic", "giving", "glaring", "glass", "gleaming", "gleeful", "gloomy", "glorious", "glossy", "glum", "godless", "godlike", "golden", "good", "gorgeous", "graceful", "gracious", "grand", "granular", "grateful", "grave", "gray", "great", "greedy", "green", "grim", "grimy", "gripping", "grizzled", "gross", "grouchy", "grounded", "growing", "growling", "grown", "grubby", "gruesome", "grumpy", "guilty", "gullible", "gummy", "hairy", "half", "handmade", "handsome", "handy", "happy", "hard", "hardy", "harmful", "harmless", "harsh", "hasty", "hateful", "haughty", "haunting", "healthy", "hearty", "heavenly", "heavy", "hefty", "helpful", "helpless", "hidden", "hideous", "high", "hoarse", "hollow", "holy", "homely", "honest", "honored", "hopeful", "horrible", "hot", "huge", "humane", "humble", "humming", "humorous", "hungry", "hurtful", "husky", "icky", "icy", "ideal", "idiotic", "idle", "idolized", "ignorant", "ill", "ill-bred", "illegal", "immense", "immoral", "impious", "impish", "impolite", "imposing", "impure", "inane", "inborn", "indecent", "indolent", "infamous", "inferior", "infinite", "informal", "innocent", "insecure", "insolent", "intent", "internal", "intrepid", "ironclad", "ironic", "itchy", "jaded", "jagged", "jaunty", "jealous", "jittery", "joint", "jolly", "jovial", "joyful", "joyous", "jubilant", "juicy", "jumbo", "jumpy", "junior", "juvenile", "keen", "key", "kind", "kindly", "klutzy", "knobby", "knotty", "knowing", "known", "kooky", "kosher", "laconic", "lame", "lanky", "large", "last", "lasting", "late", "lavish", "lawful", "lazy", "leading", "leafy", "lean", "learned", "left", "legal", "liberal", "light", "likable", "like", "likely", "limited", "limp", "limping", "linear", "lined", "liquid", "listless", "little", "live", "lively", "livid", "lone", "lonely", "long", "loose", "lopsided", "lost", "loud", "lovable", "lovely", "loving", "low", "loyal", "lucky", "luminous", "lumpy", "lustrous", "mad", "made-up", "majestic", "major", "male", "mammoth", "manly", "married", "massive", "mature", "meager", "mealy", "mean", "measly", "meaty", "medical", "mediocre", "medium", "meek", "mellow", "melodic", "menacing", "merciful", "merry", "messy", "metallic", "mild", "milky", "mindless", "minor", "minty", "miserly", "misty", "mixed", "modern", "modest", "moist", "monthly", "moody", "moral", "motherly", "muddy", "muffled", "mulish", "mundane", "murky", "muscular", "mushy", "musty", "muted", "mystical", "naive", "narrow", "nasty", "natural", "naughty", "nautical", "near", "neat", "needy", "negative", "nervous", "new", "next", "nice", "nifty", "nimble", "nippy", "noble", "noisy", "nonstop", "normal", "notable", "noted", "novel", "noxious", "numb", "nutty", "obdurate", "obedient", "obese", "obtuse", "obvious", "odd", "oddball", "odious", "offbeat", "official", "oily", "old", "only", "open", "optimal", "opulent", "orange", "orderly", "ordinary", "organic", "original", "ornate", "ornery", "orthodox", "other", "our", "outgoing", "outlying", "oval", "overdue", "pale", "paltry", "parallel", "parched", "partial", "past", "pastel", "patient", "peaceful", "pedantic", "peevish", "pensive", "peppery", "perfect", "perfumed", "periodic", "perky", "personal", "pert", "perverse", "pesky", "petty", "petulant", "phony", "physical", "piercing", "pink", "pious", "pitiful", "plain", "plastic", "playful", "pleasant", "pleased", "pleasing", "plucky", "plump", "plush", "pointed", "poised", "polished", "polite", "politic", "pompous", "poor", "popular", "portly", "posh", "positive", "possible", "potable", "powerful", "precious", "present", "pretty", "previous", "pricey", "prickly", "primary", "prime", "pristine", "private", "prize", "probable", "profane", "profound", "profuse", "prolix", "proper", "proud", "prudent", "puerile", "punctual", "pungent", "puny", "pure", "purple", "pushy", "putrid", "puzzled", "puzzling", "quaint", "queasy", "quick", "quiet", "quirky", "quixotic", "radiant", "radical", "ragged", "rapid", "rare", "rash", "rational", "raw", "ready", "real", "recent", "reckless", "red", "refined", "regal", "regular", "reliable", "relieved", "remiss", "remote", "required", "reserved", "resolute", "reticent", "reverent", "ribald", "rich", "right", "rigid", "ringed", "ripe", "roasted", "robust", "romantic", "rosy", "rotating", "rotten", "rough", "round", "rowdy", "royal", "rubbery", "ruddy", "rude", "rundown", "runny", "rural", "rustic", "rusty", "ruthless", "sad", "safe", "sage", "saintly", "salty", "same", "sandy", "sane", "sardonic", "satiric", "saucy", "scaly", "scarce", "scared", "scary", "scented", "scornful", "scratchy", "scrawny", "second", "secret", "sedate", "selfish", "sensible", "sensual", "separate", "serene", "serious", "several", "severe", "shabby", "shadowy", "shady", "shallow", "shameful", "shapely", "sharp", "shiny", "shocked", "shocking", "shoddy", "short", "showy", "shrewd", "shrewish", "shrill", "shy", "sick", "sickly", "silent", "silky", "silly", "silver", "similar", "simple", "sinful", "single", "sizzling", "skeletal", "skillful", "skinny", "sleepy", "slight", "slim", "slimy", "slippery", "slothful", "slovenly", "slow", "slushy", "small", "smart", "smoggy", "smooth", "smug", "snappy", "snarling", "sneaky", "snoopy", "sober", "sociable", "soft", "soggy", "solemn", "solid", "somber", "some", "sore", "soulful", "soupy", "sour", "Spanish", "spare", "sparse", "specific", "speedy", "spicy", "spiffy", "spirited", "spiteful", "splendid", "spotless", "spotted", "spruce", "spry", "square", "squeaky", "squiggly", "stable", "staid", "stained", "stale", "stalwart", "standard", "starchy", "stark", "starry", "steel", "steep", "sticky", "stiff", "stingy", "stoical", "stormy", "straight", "strange", "strict", "strident", "striking", "striped", "strong", "stubborn", "studious", "stunning", "stupid", "sturdy", "stylish", "suave", "subdued", "subtle", "suburban", "sudden", "sugary", "sulky", "sullen", "sunny", "super", "superb", "superior", "svelte", "sweaty", "sweet", "swift", "symbolic", "taciturn", "tactful", "talented", "tall", "tame", "tan", "tangible", "tart", "tasty", "tattered", "taut", "tedious", "teeming", "tempting", "tender", "tense", "tepid", "terrible", "terrific", "testy", "thankful", "that", "these", "thick", "thin", "third", "thirsty", "this", "thorny", "thorough", "those", "thrifty", "tidy", "tight", "timely", "timid", "timorous", "tinted", "tiny", "tired", "tolerant", "torn", "total", "tough", "tragic", "trained", "tricky", "trifling", "trim", "trivial", "troubled", "true", "trusting", "trusty", "truthful", "tubby", "twin", "ugly", "ultimate", "unaware", "uncivil", "uncommon", "unctuous", "uneven", "unfit", "unfolded", "ungainly", "ungifted", "unhappy", "uniform", "unique", "united", "unkempt", "unknown", "unlawful", "unlined", "unlucky", "unmanly", "unripe", "unruly", "unstable", "unsteady", "unsung", "untidy", "untimely", "untried", "untrue", "unused", "unusual", "unwieldy", "upbeat", "upright", "upset", "urban", "urbane", "usable", "used", "useful", "useless", "utilized", "utter", "vacant", "vacuous", "vague", "vain", "valid", "valorous", "valuable", "vapid", "variable", "vast", "velvety", "venal", "vengeful", "vibrant", "vicious", "vigilant", "vigorous", "vile", "violent", "violet", "virile", "virtual", "virtuous", "visible", "vital", "vivid", "vulgar", "wan", "warlike", "warm", "warped", "wary", "waspish", "wasteful", "watchful", "watery", "wavy", "weak", "wealthy", "weary", "webbed", "wee", "weekly", "weepy", "weighty", "weird", "welcome", "well-lit", "well-off", "wet", "which", "white", "whole", "whopping", "wicked", "wide", "wiggly", "wild", "willful", "willing", "wilted", "wily", "winding", "windy", "winged", "winsome", "wiry", "wise", "witty", "wobbly", "woeful", "wooden", "woozy", "wordy", "worldly", "worn", "worried", "worse", "worst", "worthy", "wrathful", "wretched", "writhing", "wrong", "wry", "yawning", "yearly", "yellow", "young", "youthful", "yummy", "zany", "zealous", "zesty", "zigzag");
    private static final List<String> LASTNAMES = Arrays.asList("access", "ache", "act", "address", "admirer", "adult", "aim", "alert", "alfalfa", "alloy", "allspice", "ally", "aluminum", "amber", "amethyst", "amigo", "ancestor", "angel", "angelica", "anise", "annato", "annulus", "answer", "antimony", "anybody", "anyone", "apricot", "aqua", "arc", "arrest", "arugula", "attack", "auburn", "auction", "avocado", "azure", "baby", "back", "backer", "bail", "balance", "balloon", "ban", "bandage", "bank", "bare", "bargain", "basil", "battle", "bay", "beam", "beans", "bear", "beat", "beau", "beets", "beige", "bend", "benefit", "berbere", "black", "blame", "blast", "bleach", "block", "bloom", "blow", "blue", "board", "bomb", "borage", "bother", "bounce", "bow", "box", "boy", "brass", "bravery", "bread", "break", "breed", "bride", "broccoli", "bronze", "brother", "brown", "brush", "bud", "buddy", "buff", "bump", "burn", "buy", "cake", "call", "camp", "capers", "caraway", "cardamon", "cardinal", "cardioid", "care", "caring", "carmine", "carob", "carrot", "catch", "cause", "cayenne", "celadon", "celeriac", "celery", "cerise", "cerulean", "champion", "change", "chant", "charcoal", "chard", "charge", "charity", "cheat", "check", "cheer", "cherub", "chervil", "chicory", "child", "children", "chip", "chives", "chrome", "chromium", "chum", "cicely", "cilantro", "cinnamon", "circle", "citizen", "claim", "clan", "clip", "cloud", "clove", "clue", "coach", "cohort", "colour", "comb", "comfort", "compadre", "comrade", "concern", "cone", "contact", "contrast", "control", "cook", "coop", "copper", "copy", "coral", "cost", "count", "courage", "courtesy", "cover", "coworker", "crack", "crash", "crate", "cream", "credit", "crescent", "cress", "crew", "crimson", "crony", "crush", "cube", "cuboid", "cucumber", "cure", "curl", "curve", "customer", "cut", "cyan", "cycle", "cylinder", "daikon", "dam", "damage", "dance", "dark", "deal", "decagon", "decay", "decrease", "delay", "delight", "demand", "denim", "design", "devotion", "dial", "die", "dignity", "dill", "disc", "dislike", "display", "dive", "divorce", "dock", "dot", "double", "doubt", "drain", "draw", "dream", "dress", "drill", "drink", "drive", "duck", "dude", "dump", "dust", "duty", "dye", "earnest", "ebony", "echo", "ecru", "eggplant", "ellipse", "email", "emerald", "employee", "end", "endive", "enemy", "epazote", "escape", "esteem", "estimate", "ethical", "everyone", "exchange", "excuse", "exhibit", "eye", "face", "fall", "family", "favor", "fax", "fear", "feel", "fellow", "fennel", "fiancee", "fight", "file", "fill", "film", "finish", "fish", "fix", "flap", "flash", "flatmate", "float", "flood", "floss", "flow", "flower", "fly", "focus", "foe", "fold", "folks", "fool", "force", "form", "frame", "freeze", "freshman", "friend", "frown", "fuchsia", "function", "galangal", "garden", "garlic", "gaze", "gel", "gent", "ginger", "girl", "glue", "gnomon", "gold", "good", "goodwill", "gourd", "grace", "grate", "gray", "grease", "green", "greens", "grey", "grill", "grimace", "grin", "grip", "groom", "guard", "guess", "guide", "gunmetal", "guy", "hammer", "hand", "handle", "harissa", "harm", "harness", "hate", "head", "heap", "heart", "heat", "helix", "help", "heptagon", "hexagon", "hide", "hike", "hit", "hold", "honesty", "honor", "hop", "hope", "hose", "hue", "hug", "human", "humanity", "humility", "humor", "hunt", "hurry", "hyssop", "ice", "idealism", "impact", "in-law", "inch", "increase", "indigo", "industry", "infant", "insult", "interest", "interval", "iridium", "iron", "itch", "ivory", "jade", "jail", "jam", "jasmine", "jet", "jicama", "joke", "joy", "judge", "jump", "junior", "juniper", "justice", "kale", "keep", "khaki", "kick", "kid", "kin", "kindness", "kiss", "kite", "kith", "knit", "knock", "knot", "kohlrabi", "label", "lad", "laddie", "lady", "land", "lass", "lassie", "last", "laugh", "lavender", "lead", "leap", "leek", "lemon", "leniency", "lentils", "lettuce", "level", "license", "licorice", "lie", "lift", "light", "lilac", "lime", "limit", "line", "link", "load", "loan", "lock", "look", "lovage", "love", "lover", "loyalty", "lozenge", "mace", "magenta", "mahogany", "mail", "maize", "make", "man", "march", "marjoram", "mark", "maroon", "match", "mate", "matter", "mauve", "mean", "measure", "men", "mercury", "mercy", "metal", "milk", "mind", "mine", "minor", "mint", "miss", "mistake", "modesty", "moor", "moral", "morality", "mortal", "move", "mug", "mushroom", "mustard", "nail", "name", "need", "neighbor", "nest", "newborn", "nice", "nobility", "noble", "nobody", "nonagon", "notch", "note", "notice", "number", "nutmeg", "object", "occupant", "ocher", "octagon", "offer", "oil", "okra", "olive", "onion", "openness", "opponent", "orange", "orb", "orchid", "order", "oregano", "oval", "pack", "pad", "paddle", "paint", "pal", "pale", "paprika", "park", "parsley", "parsnip", "part", "partner", "pass", "paste", "pastel", "pat", "patience", "patron", "pause", "pay", "pea", "peach", "peanut", "peapod", "pedal", "peel", "pelt", "pentagon", "people", "pepper", "peppers", "permit", "person", "pewter", "phone", "pick", "pickle", "pine", "pink", "place", "plan", "plane", "plant", "platinum", "play", "plow", "plug", "point", "poke", "polygon", "pop", "populace", "post", "potato", "practice", "praise", "present", "preteen", "primary", "prism", "probity", "process", "produce", "promise", "protest", "prudence", "puce", "pull", "pump", "pumpkin", "punch", "purity", "purple", "push", "pyramid", "question", "quiet", "quilt", "quiz", "race", "radish", "rain", "rainbow", "raise", "rant", "rate", "ray", "reach", "reason", "record", "red", "reign", "relative", "rent", "repair", "reply", "report", "request", "resident", "respect", "retiree", "rhombus", "rhubarb", "rhyme", "ring", "riot", "risk", "rival", "rock", "rocket", "roll", "romaine", "roommate", "rose", "rosemary", "round", "row", "ruby", "rue", "ruin", "rule", "run", "russet", "rust", "rutabaga", "saffron", "sage", "sail", "salad", "salmon", "salsa", "salsify", "sand", "sapphire", "savoury", "saw", "scallion", "scare", "scarlet", "scratch", "screw", "search", "season", "seaweed", "sector", "senior", "sense", "sepia", "shade", "shallot", "shampoo", "shamrock", "shape", "share", "shelter", "shock", "shop", "show", "sidekick", "sienna", "sign", "signal", "silence", "silver", "sin", "sip", "sister", "skate", "sketch", "ski", "slate", "slice", "slide", "slip", "smell", "smile", "smirk", "smoke", "snack", "snow", "somebody", "someone", "sorrel", "soul", "sound", "soybean", "span", "spectrum", "sphere", "spheroid", "spinach", "sponsor", "spot", "spouse", "spray", "sprout", "sprouts", "spuds", "square", "squash", "stain", "stamp", "stand", "star", "start", "state", "steady", "steel", "steer", "step", "sting", "stop", "store", "storm", "stranger", "strength", "stress", "strip", "stroke", "struggle", "study", "stuff", "stunt", "suit", "sumac", "supply", "support", "surf", "surprise", "swap", "swing", "swivel", "symmetry", "sympathy", "tack", "tact", "talk", "tan", "taro", "tarragon", "taste", "taupe", "teal", "teammate", "tear", "tease", "teen", "teenager", "test", "thistle", "thought", "thrift", "thunder", "thyme", "tick", "tie", "time", "tin", "tint", "tip", "tire", "titanium", "toast", "toddler", "tomato", "topaz", "torus", "tot", "touch", "tour", "tow", "trace", "track", "trade", "train", "trap", "travel", "treat", "triangle", "tribe", "trick", "trim", "trust", "tuber", "tug", "tumeric", "tungsten", "turn", "turnip", "tween", "twist", "tyke", "type", "umber", "unity", "upstage", "uranium", "urchin", "use", "vacuum", "value", "vanilla", "violet", "viridian", "virtuous", "visit", "vitality", "voice", "vote", "wake", "walk", "waltz", "wasabi", "watch", "water", "wave", "wear", "wedge", "wheat", "whip", "whisper", "whistle", "white", "wick", "wink", "wire", "wisdom", "wish", "wisteria", "woman", "women", "wonder", "woodruff", "work", "workmate", "worry", "worthy", "wrap", "wreck", "yam", "yawn", "yellow", "yield", "youth", "zeal", "zinc", "zucchini");

    public static String name() {
        String name = "12345678901234567";
        Random rand = new Random();
        while (name.length() > 16) {
            name = FIRSTNAMES.get(rand.nextInt(FIRSTNAMES.size())) + "_" + LASTNAMES.get(rand.nextInt(LASTNAMES.size()));
        }
        return name;
    }
}
