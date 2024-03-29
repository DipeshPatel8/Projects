﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Dynamic;

// ============================================================================
// (c) Sandy Bultena 2018
// * Released under the GNU General Public License
// ============================================================================


namespace Budget
{
    // ====================================================================
    // CLASS: HomeBudget
    //        - Combines categories Class and expenses Class
    //        - One File defines Category and Budget File
    //        - etc
    // ====================================================================

    public class HomeBudget
    {
        private string _FileName;
        private string _DirName;

        // ====================================================================
        // Properties
        // ===================================================================

        // Properties (location of files etc)
        public String FileName { get { return _FileName; } }
        public String DirName { get { return _DirName; } }
        public String PathName
        {
            get
            {
                if (_FileName != null && _DirName != null)
                {
                    return Path.GetFullPath(_DirName + "\\" + _FileName);
                }
                else
                {
                    return null;
                }
            }
        }

        // Properties (categories and expenses object)
        public CategoriesManager categories { get; set; }
        public Expenses expenses { get; set; }

        // -------------------------------------------------------------------
        // Constructor (new... default categories, no expenses)
        // -------------------------------------------------------------------
        public HomeBudget()
        {
            categories = new CategoriesManager();
            expenses = new Expenses();
        }

        // -------------------------------------------------------------------
        // Constructor (existing budget ... must specify file)
        // -------------------------------------------------------------------
        public HomeBudget(String budgetFileName)
        {
            categories = new CategoriesManager();
            expenses = new Expenses();
            ReadFromFile(budgetFileName);
        }

        #region OpenNewAndSave
        // ---------------------------------------------------------------
        // Read
        // ---------------------------------------------------------------
        public void ReadFromFile(String budgetFileName)
        {
            // ---------------------------------------------------------------
            // read the budget file and process
            // ---------------------------------------------------------------
            try
            {
                // get filepath name (throws exception if it doesn't exist)
                budgetFileName = BudgetFiles.VerifyReadFromFileName(budgetFileName, "");

                // If file exists, read it
                string[] filenames = System.IO.File.ReadAllLines(budgetFileName);

                // ----------------------------------------------------------------
                // Save information about budget file
                // ----------------------------------------------------------------
                string folder = Path.GetDirectoryName(budgetFileName);
                _FileName = Path.GetFileName(budgetFileName);

                // read the expenses and categories from their respective files
                categories.ReadFromFile(folder + "\\" + filenames[0]);
                expenses.ReadFromFile(folder + "\\" + filenames[1]);

                // Save information about budget file
                _DirName = Path.GetDirectoryName(budgetFileName);
                _FileName = Path.GetFileName(budgetFileName);

            }

            // ----------------------------------------------------------------
            // throw new exception if we cannot get the info that we need
            // ----------------------------------------------------------------
            catch (Exception e)
            {
                throw new Exception("Could not read budget info: \n" + e.Message);
            }

        }

        // ====================================================================
        // save to a file
        // ====================================================================
        public void SaveToFile(String filepath)
        {

            // ---------------------------------------------------------------
            // just in case filepath doesn't exist, reset path info
            // ---------------------------------------------------------------
            _DirName = null;
            _FileName = null;

            // ---------------------------------------------------------------
            // get filepath name (throws exception if it doesn't exist)
            // ---------------------------------------------------------------
            filepath = BudgetFiles.VerifyWriteToFileName(filepath, "");

            String path = Path.GetDirectoryName(Path.GetFullPath(filepath));
            String file = Path.GetFileNameWithoutExtension(filepath);
            String ext = Path.GetExtension(filepath);

            // ---------------------------------------------------------------
            // construct file names for expenses and categories
            // ---------------------------------------------------------------
            String expensepath = path + "\\" + file + "_expenses" + ".exps";
            String categorypath = path + "\\" + file + "_categories" + ".cats";

            // ---------------------------------------------------------------
            // save the expenses and budgets into their own files
            // ---------------------------------------------------------------
            expenses.SaveToFile(expensepath);
            categories.SaveToFile(categorypath);

            // ---------------------------------------------------------------
            // save filenames of expenses and categories to budget file
            // ---------------------------------------------------------------
            string[] files = { Path.GetFileName(categorypath), Path.GetFileName(expensepath) };
            System.IO.File.WriteAllLines(filepath, files);

            // ----------------------------------------------------------------
            // save filename info for later use
            // ----------------------------------------------------------------
            _DirName = path;
            _FileName = Path.GetFileName(filepath);
        }
        #endregion OpenNewAndSave

        #region GetList



        // ============================================================================
        // Get all expenses list
        // ============================================================================
        public List<BudgetItem> GetExpenses(DateTime? Start, DateTime? End, bool FilterFlag, int CategoryID)
        {
            // ------------------------------------------------------------------------
            // return joined list within time frame
            // ------------------------------------------------------------------------
            Start = Start ?? new DateTime(1900, 1, 1);
            End = End ?? new DateTime(2500, 1, 1);

            var query = from c in categories.List()
                        join e in expenses.List() on c.Id equals e.Category
                        where e.Date >= Start && e.Date <= End
                        select new { CatId = c.Id, ExpId = e.Id, e.Date, Category = c.Description, e.Description, e.Amount };

            // ------------------------------------------------------------------------
            // create a BudgetItem list with totals,
            // ------------------------------------------------------------------------
            List<BudgetItem> items = new List<BudgetItem>();
            Double total = 0;

            foreach (var e in query.OrderBy(q => q.Date))
            {
                // filter out unwanted categories if filter flag is on
                if (FilterFlag && CategoryID != e.CatId)
                {
                    continue;
                }

                // keep track of running totals
                total = total - e.Amount;
                items.Add(new BudgetItem
                {
                    CategoryID = e.CatId,
                    ExpenseID = e.ExpId,
                    ShortDescription = e.Description,
                    Date = e.Date,
                    Amount = -e.Amount,
                    Category = e.Category,
                    Balance = total
                });
            }

            return items;
        }

        // ============================================================================
        // Group all expenses month by month
        // ============================================================================
        public List<BudgetItemsByMonth> GetExpensesByMonth(DateTime? Start, DateTime? End, bool FilterFlag, int CategoryID)
        {
            // -----------------------------------------------------------------------
            // get all items first
            // -----------------------------------------------------------------------
            List<BudgetItem> items = GetExpenses(Start, End, FilterFlag, CategoryID);

            // -----------------------------------------------------------------------
            // Group by year/month
            // -----------------------------------------------------------------------
            var GroupedByMonth = items.GroupBy(c => c.Date.Year.ToString("D4") + "/" + c.Date.Month.ToString("D2"));

            // -----------------------------------------------------------------------
            // create new list
            // -----------------------------------------------------------------------
            var summary = new List<BudgetItemsByMonth>();
            foreach (var MonthGroup in GroupedByMonth)
            {
                // calculate total for this month, and create list of details
                double total = 0;
                var details = new List<BudgetItem>();
                foreach (var item in MonthGroup)
                {
                    total = total + item.Amount;
                    details.Add(item);
                }

                // Add new BudgetItemsByMonth to our list
                summary.Add(new BudgetItemsByMonth
                {
                    Month = MonthGroup.Key,
                    Details = details,
                    Total = total
                });
            }

            return summary;
        }

        // ============================================================================
        // Group all expenses by category
        // ============================================================================
        public List<BudgetItemsByCategory> GetExpensesByCategory(DateTime? Start, DateTime? End, bool FilterFlag, int CategoryID)
        {
            // -----------------------------------------------------------------------
            // get all items first
            // -----------------------------------------------------------------------
            List<BudgetItem> items = GetExpenses(Start, End, FilterFlag, CategoryID);

            // -----------------------------------------------------------------------
            // Group by Category
            // -----------------------------------------------------------------------
            var GroupedByCategory = items.GroupBy(c => c.Category);

            // -----------------------------------------------------------------------
            // create new list
            // -----------------------------------------------------------------------
            var summary = new List<BudgetItemsByCategory>();
            foreach (var CategoryGroup in GroupedByCategory.OrderBy(g => g.Key))
            {
                // calculate total for this category, and create list of details
                double total = 0;
                var details = new List<BudgetItem>();
                foreach (var item in CategoryGroup)
                {
                    total = total + item.Amount;
                    details.Add(item);
                }

                // Add new BudgetItemsByCategory to our list
                summary.Add(new BudgetItemsByCategory
                {
                    Category = CategoryGroup.Key,
                    Details = details,
                    Total = total
                });
            }

            return summary;
        }



        // ============================================================================
        // Group all expenses by category and Month
        // ============================================================================
        public List<ExpandoObject> GetExpensesByCategoryAndMonth(DateTime? Start, DateTime? End, bool FilterFlag, int CategoryID)
        {
            // -----------------------------------------------------------------------
            // get all items by month 
            // -----------------------------------------------------------------------
            List<BudgetItemsByMonth> GroupedByMonth = GetExpensesByMonth(Start, End, FilterFlag, CategoryID);

            // -----------------------------------------------------------------------
            // loop over each month
            // -----------------------------------------------------------------------
            var summary = new List<ExpandoObject>();
            var totalscat = new Dictionary<String, Double>();

            foreach (var MonthGroup in GroupedByMonth)
            {
                // create row object for this month
                dynamic row = new ExpandoObject();
                row.Month = MonthGroup.Month;
                row.Total = MonthGroup.Total;

                // break up the month details into categories
                var GroupedByCategory = MonthGroup.Details.GroupBy(c => c.Category);

                // -----------------------------------------------------------------------
                // loop over each category
                // -----------------------------------------------------------------------
                foreach (var CategoryGroup in GroupedByCategory.OrderBy(g => g.Key))
                {

                    // calculate totals for the cat/month, and create list of details
                    double total = 0;
                    var details = new List<BudgetItem>();

                    foreach (var item in CategoryGroup)
                    {
                        total = total + item.Amount;
                        details.Add(item);
                    }

                    // add new properties and values to our row object
                    ((IDictionary<String, Object>)row).Add("details:" + CategoryGroup.Key, details);
                    ((IDictionary<String, Object>)row).Add(CategoryGroup.Key, total);

                    // keep track of totals for each category
                    if (totalscat.TryGetValue(CategoryGroup.Key, out Double CurrentCatTotal))
                    {
                        totalscat[CategoryGroup.Key] = CurrentCatTotal + total;
                    }
                    else
                    {
                        totalscat[CategoryGroup.Key] = total;
                    }
                }

                // add row to collection
                summary.Add(row);
            }
            // ---------------------------------------------------------------------------
            // add final row which is the totals for each category
            // ---------------------------------------------------------------------------
            dynamic totalrow = new ExpandoObject();
            totalrow.Month = "TOTALS";

            foreach (var cat in categories.List())
            {
                try
                {
                    ((IDictionary<String, Object>)totalrow).Add(cat.Description, totalscat[cat.Description]);
                }
                catch { }
            }
            summary.Add(totalrow);


            return summary;
        }




        #endregion GetList

    }
}
