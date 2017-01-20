parser grammar NumericRules;

// ********** numeric rules **********

// a number between 00 and 59 inclusive, with a mandatory 0 prefix before numbers 0-9
int_00_to_59_mandatory_prefix
  : (INT_00
  | int_01_to_12
  | int_13_to_23
  | int_24_to_31
  | int_32_to_59)
  ;

// a number between 00 and 99 inclusive, with a mandatory 0 prefix before numbers 0-9
int_00_to_99_mandatory_prefix
  : (int_00_to_59_mandatory_prefix | int_60_to_99)
  ;

// a number between 1 and 12 inclusive, with an optional 0 prefix before numbers 1-9
int_01_to_12_optional_prefix
  : (int_1_to_9 | int_01_to_12)
  ;

// a number between 0 and 23 inclusive, with an optional 0 prefix before numbers 0-9
int_00_to_23_optional_prefix
  : (INT_00
  | INT_0
  | int_1_to_9
  | int_01_to_12
  | int_13_to_23)
  ;

// a number between 1 and 31 inclusive, with an optional 0 prefix before numbers 1-9
int_01_to_31_optional_prefix
  : (int_01_to_12
  | int_1_to_9
  | int_13_to_23
  | int_24_to_31)
  ;

// a number with exactly four digits
int_four_digits
  : int_00_to_99_mandatory_prefix int_00_to_99_mandatory_prefix
  ;

// a number between one and thirty-one either spelled-out, or as an
// integer with an optional 0 prefix for numbers betwen 1 and 9
spelled_or_int_01_to_31_optional_prefix
  : int_01_to_31_optional_prefix
  | spelled_one_to_thirty_one
  ;

spelled_or_ordinal_or_int_01_to_31_optional_prefix
  : spelled_or_int_01_to_31_optional_prefix
  | spelled_first_to_thirty_first
  ;

// a number between 1 and 9999 either spelled-out, or as an
// integer with an optional 0 prefix for numbers betwen 1 and 9
spelled_or_int_optional_prefix
  : spelled_one_to_thirty_one // TODO expand this spelled range to at least ninety-nine
  | (int_01_to_31_optional_prefix | int_32_to_59 | int_60_to_99)
    ( INT_0 | INT_00 | int_01_to_31_optional_prefix | int_32_to_59 | int_60_to_99)?
  ;

// a spelled number between one and thirty-one (one, two, etc.)
spelled_one_to_thirty_one returns [int numVal]
  : ONE                         {$numVal = 1;}
  | TWO                         {$numVal = 2;}
  | THREE                       {$numVal = 3;}
  | FOUR                        {$numVal = 4;}
  | FIVE                        {$numVal = 5;}
  | SIX                         {$numVal = 6;}
  | SEVEN                       {$numVal = 7;}
  | EIGHT                       {$numVal = 8;}
  | NINE                        {$numVal = 9;}
  | TEN                         {$numVal = 10;}
  | ELEVEN                      {$numVal = 11;}
  | TWELVE                      {$numVal = 12;}
  | THIRTEEN                    {$numVal = 13;}
  | FOURTEEN                    {$numVal = 14;}
  | FIFTEEN                     {$numVal = 15;}
  | SIXTEEN                     {$numVal = 16;}
  | SEVENTEEN                   {$numVal = 17;}
  | EIGHTEEN                    {$numVal = 18;}
  | NINETEEN                    {$numVal = 19;}
  | (TWENTY WHITE_SPACE ONE)    {$numVal = 21;}
  | TWENTY DASH? ONE            {$numVal = 21;}
  | (TWENTY WHITE_SPACE TWO)    {$numVal = 22;}
  | TWENTY DASH? TWO            {$numVal = 22;}
  | (TWENTY WHITE_SPACE THREE)  {$numVal = 23;}
  | TWENTY DASH? THREE          {$numVal = 23;}
  | (TWENTY WHITE_SPACE FOUR)   {$numVal = 24;}
  | TWENTY DASH? FOUR           {$numVal = 24;}
  | (TWENTY WHITE_SPACE FIVE)   {$numVal = 25;}
  | TWENTY DASH? FIVE           {$numVal = 25;}
  | (TWENTY WHITE_SPACE SIX)    {$numVal = 26;}
  | TWENTY DASH? SIX            {$numVal = 26;}
  | (TWENTY WHITE_SPACE SEVEN)  {$numVal = 27;}
  | TWENTY DASH? SEVEN          {$numVal = 27;}
  | (TWENTY WHITE_SPACE EIGHT)  {$numVal = 28;}
  | TWENTY DASH? EIGHT          {$numVal = 28;}
  | (TWENTY WHITE_SPACE NINE)   {$numVal = 29;}
  | TWENTY DASH? NINE           {$numVal = 29;}
  | TWENTY                      {$numVal = 20;}
  | (THIRTY WHITE_SPACE ONE)    {$numVal = 31;}
  | THIRTY DASH? ONE            {$numVal = 31;}
  | THIRTY                      {$numVal = 30;}
  ;

// a spelled number in sequence between first and thirty-first
spelled_first_to_thirty_first returns [int numVal]
  : (FIRST       | INT_1 ST)                                {$numVal = 1;}
  | (SECOND      | INT_2 ND)                                {$numVal = 2;}
  | (THIRD       | INT_3 RD)                                {$numVal = 3;}
  | (FOURTH      | INT_4 TH)                                {$numVal = 4;}
  | (FIFTH       | INT_5 TH)                                {$numVal = 5;}
  | (SIXTH       | INT_6 TH)                                {$numVal = 6;}
  | (SEVENTH     | INT_7 TH)                                {$numVal = 7;}
  | (EIGHTH      | INT_8 TH)                                {$numVal = 8;}
  | (NINTH       | INT_9 TH)                                {$numVal = 9;}
  | (TENTH       | INT_10 TH)                               {$numVal = 10;}
  | (ELEVENTH    | INT_11 TH)                               {$numVal = 11;}
  | (TWELFTH     | INT_12 TH)                               {$numVal = 12;}
  | (THIRTEENTH  | INT_13 TH)                               {$numVal = 13;}
  | (FOURTEENTH  | INT_14 TH)                               {$numVal = 14;}
  | (FIFTEENTH   | INT_15 TH)                               {$numVal = 15;}
  | (SIXTEENTH   | INT_16 TH)                               {$numVal = 16;}
  | (SEVENTEENTH | INT_17 TH)                               {$numVal = 17;}
  | (EIGHTEENTH  | INT_18 TH)                               {$numVal = 18;}
  | (NINETEENTH  | INT_19 TH)                               {$numVal = 19;}
  | (TWENTIETH   | INT_20 TH)                               {$numVal = 20;}
  | ((TWENTY (DASH | WHITE_SPACE)? FIRST)   | INT_21 ST)    {$numVal = 21;}
  | ((TWENTY (DASH | WHITE_SPACE)? SECOND)  | INT_22 ND)    {$numVal = 22;}
  | ((TWENTY (DASH | WHITE_SPACE)? THIRD)   | INT_23 RD)    {$numVal = 23;}
  | ((TWENTY (DASH | WHITE_SPACE)? FOURTH)  | INT_24 TH)    {$numVal = 24;}
  | ((TWENTY (DASH | WHITE_SPACE)? FIFTH)   | INT_25 TH)    {$numVal = 25;}
  | ((TWENTY (DASH | WHITE_SPACE)? SIXTH)   | INT_26 TH)    {$numVal = 26;}
  | ((TWENTY (DASH | WHITE_SPACE)? SEVENTH) | INT_27 TH)    {$numVal = 27;}
  | ((TWENTY (DASH | WHITE_SPACE)? EIGHTH)  | INT_28 TH)    {$numVal = 28;}
  | ((TWENTY (DASH | WHITE_SPACE)? NINTH)   | INT_29 TH)    {$numVal = 29;}
  | (THIRTIETH | INT_30 TH)                                 {$numVal = 30;}
  | ((THIRTY (DASH | WHITE_SPACE)? FIRST)   | INT_31 ST)    {$numVal = 31;}
  ;

int_60_to_99
  : INT_60 | INT_61 | INT_62 | INT_63 | INT_64 | INT_65 | INT_66 | INT_67 | INT_68
  | INT_69 | INT_70 | INT_71 | INT_72 | INT_73 | INT_74 | INT_75 | INT_76 | INT_77
  | INT_78 | INT_79 | INT_80 | INT_81 | INT_82 | INT_83 | INT_84 | INT_85 | INT_86
  | INT_87 | INT_88 | INT_89 | INT_90 | INT_91 | INT_92 | INT_93 | INT_94 | INT_95
  | INT_96 | INT_97 | INT_98 | INT_99
  ;

int_32_to_59
  : INT_32 | INT_33 | INT_34 | INT_35 | INT_36 | INT_37 | INT_38 | INT_39 | INT_40
  | INT_41 | INT_42 | INT_43 | INT_44 | INT_45 | INT_46 | INT_47 | INT_48 | INT_49
  | INT_50 | INT_51 | INT_52 | INT_53 | INT_54 | INT_55 | INT_56 | INT_57 | INT_58
  | INT_59
  ;

int_24_to_31
  : INT_24 | INT_25 | INT_26 | INT_27 | INT_28 | INT_29  | INT_30 | INT_31
  ;

int_13_to_23
  : INT_13 | INT_14 | INT_15 | INT_16 | INT_17 | INT_18 | INT_19  | INT_20 | INT_21
  | INT_22 | INT_23
  ;

int_01_to_12
  : INT_01 | INT_02 | INT_03 | INT_04 | INT_05 | INT_06 | INT_07 | INT_08 | INT_09
  | INT_10 | INT_11 | INT_12
  ;

int_1_to_9
  : INT_1  | INT_2  | INT_3  | INT_4  | INT_5  | INT_6  | INT_7  | INT_8  | INT_9
  ;

int_1_to_5
  : INT_1  | INT_2  | INT_3  | INT_4  | INT_5
  ;
